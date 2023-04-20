package org.mmgroup.server;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.mmgroup.boardfactory.BoardFactory;
import org.mmgroup.boardfactory.TwoPlayersChineseCheckersFactory;
import org.mmgroup.gamelogic.Board;
import org.mmgroup.gamelogic.GameRules;
import org.mmgroup.gamelogic.JumpMove;
import org.mmgroup.gamelogic.NormalMove;
import org.mmgroup.gamelogic.OutOfBaseAntiMove;
import org.mmgroup.gamelogic.Vector2;

/**
 * Server's game manager, it manages turns, wins, and game board
 * @author Manuela.Markowska
 *
 */
public class GameLobby {
  Board board;
  Server server;
  
  public GameRules moveRules = new GameRules();
  private ArrayList<String> rulesNames = new ArrayList<String>();
  
  public GameLobby(Server server) {
    this.server = server;
  }
  
  public void setBoard(Board board) {
    this.board = board;
  }
  
  public Board getBoard() {
    return board;
  }
  
  /**
   * adds move rule
   * @param ruleName
   */
  public void addMoveRule(String ruleName) {
    if(ruleName.equals("normalMove")) {
      moveRules.addMoveRuleOption(new NormalMove());
    }else if(ruleName.equals("jumpMove")) {
      moveRules.addMoveRuleOption(new JumpMove());
    }else if(ruleName.equals("outOfWinAntiMove")) {
      moveRules.addMoveRuleOption(new OutOfBaseAntiMove());
    }else {
      System.out.println("SERVER WARNING: proba dodania nie istniejacej zasady");
      return;
    }
    rulesNames.add(ruleName);
  }
  
  /**
   * Starts game and creates board,rules and send them to clients
   */
  public void startGame() {
    /*
     * Trzeba ustawic nieaktywne pionki i pola fabryczka czy cus
     */
    BoardFactory bf = createFactory();
    Board board = bf.buildBoard();
    this.setBoard(board);
    board.winCondition = bf.getWinCondition();
    //sendCustomBoard();
    sendBoardFromFactory();
    sendRules();
    /*
     * 
     */
    this.gameLoop();
  }
  
  /**
   * Creates factory based on number of players
   * @return
   */
  BoardFactory createFactory() {
    if(server.getNumberOfPlayers()==2) {
      return new TwoPlayersChineseCheckersFactory();
    }else if(server.getNumberOfPlayers()==3) {
      //TO DO
    }else if(server.getNumberOfPlayers()==4) {
      //TO DO
    }else if(server.getNumberOfPlayers()==6) {
      //TO DO
    }
    return null;
    
  }
  
  /**
   * Order clients to create board using the same factory as server
   */
  void sendBoardFromFactory() {
    server.broadcast("createBoardFactory;"+server.getNumberOfPlayers());
  }
  
  /**
   * Sends server move rules
   */
  void sendRules() {
    for(String ruleName: rulesNames) {
      server.broadcast("addRule;"+ruleName);
    }
  }
  
  /**
   * Sends custom board which user cannot create using his factories
   */
  void sendCustomBoard() {
    server.broadcast("createBoard;"+board.getWidth()+";"+board.getHeight());
    for(int i=0;i<board.getWidth();i++) {
      for(int j=0;j<board.getHeight();j++) {
        if(board.Grid[i][j].getActive()) {
          server.broadcast("setFieldActive;"+i+";"+j+";1");
          if(board.Grid[i][j].getPawn()!=null) {
            server.broadcast("insertPawn;"+i+";"+j+";"+board.Grid[i][j].getPawn().getOwnerId());
          }
        }else {
          server.broadcast("setFieldActive;"+i+";"+j+";0");
        }
      }
    }
  }
  
  /**
   * Game loop
   */
  void gameLoop() {
    //server.numberOfPlayers;
    Random rd = new Random();
    int rand = Math.abs(rd.nextInt());
    System.out.println("SERVER: zaczyna gracz o id " + rand%server.numberOfPlayers);
    int turaGracza = rand%server.numberOfPlayers;
    int winnersCount = 0;
    boolean czyKoniecRozgrywki = false;
    while(!czyKoniecRozgrywki) {
      server.getAllPlayers().get(turaGracza).setTurn(true);
      server.getAllPlayers().get(turaGracza).movedThisTurn = false;
      server.broadcast("newTurn;"+turaGracza);
      System.out.println("SERVER: Tura Gracza o ID: " + turaGracza);
      /*
       * Czekanie az gracz skonczy ture lub jesli status==false (gracz wygral i juz nie moze sie ruszyc)
       */
      ConnectedPlayer currentPlayer = server.getAllPlayers().get(turaGracza);
      while(currentPlayer.isItsTurn() && currentPlayer.getPlayingStatus()) {
        Wait(1);
        //System.out.println("Oczekiwanie na gracza numer: " + server.getAllPlayers().get(turaGracza).getId() + " " + turaGracza + " - ");
      };
      if(checkIfWinner(currentPlayer.getId())) {
        System.out.println("ZWYCIEZYL GRACZ " + currentPlayer.getId());
        winnersCount++;
        if(winnersCount+1==server.getNumberOfPlayers()) {
          czyKoniecRozgrywki = true;
        }
        /*
         * GRACZ WYGRAL POWIADOM WSZYSTKICH
         */
      }
      System.out.println("SERVER: GRACZ o ID: " + turaGracza + " zakonczyl ture ");
      turaGracza = turaGracza + 1;
      turaGracza = turaGracza % server.numberOfPlayers;
      System.out.println(turaGracza);
    }
    System.out.println("Koniec gry");
    /*
     * KONIEC GRY POWIADOM WSZYSTKICH
     */
  }
  
  /**
   * Waits given seconds
   * @param sec
   */
  public void Wait(int sec) {
    try {
      TimeUnit.SECONDS.sleep(sec);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Check if moves is legal
   * @param fromX
   * @param fromY
   * @param toX
   * @param toY
   * @param movedThisTurn
   * @return 2 if legal but move force to end turn
   *         1 if legal
   *         0 if illegal
   */
  public int checkIfMoveIsLegal(int fromX,int fromY,int toX,int toY, boolean movedThisTurn) {
    ArrayList<Vector2> possMoves = moveRules.getAvailableMovesForPos(this.board, fromX, fromY, movedThisTurn);
    /*
     * Sprawdzanie czy ruch byl legalny
     */
    for(Vector2 move: possMoves) {
      if(move.x == toX && move.y == toY) {
        if(move.forceTurnAfterThisMove) {
          return 2; // Legalny, konczacy ture
        }
        return 1; // Legalny, niekonczacy ture
      }
    }
    return 0; //Nielegalny
  }
  
  /**
   * Check if player has won the game
   * @param playerId
   * @return
   */
  public boolean checkIfWinner(int playerId){
    for(int i=0;i<board.winCondition.length;i++) {
      for(int j=0;j<board.winCondition[i].length;j++) {
          int number = board.winCondition[i][j] - 2;
          if(number==playerId) {
            
            if(board.Grid[j][i].getPawn() == null) {
              return false;
            }else if(board.Grid[j][i].getPawn().getOwnerId() != playerId) {
              return false;
            }
            
          }
      }
    }
    return true;
  };
}


