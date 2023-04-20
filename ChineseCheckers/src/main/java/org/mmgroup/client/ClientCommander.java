package org.mmgroup.client;

import org.mmgroup.UI.GUI;
import org.mmgroup.boardfactory.BoardFactory;
import org.mmgroup.boardfactory.TwoPlayersChineseCheckersFactory;
import org.mmgroup.gamelogic.Board;
import org.mmgroup.gamelogic.Game;
import org.mmgroup.gamelogic.JumpMove;
import org.mmgroup.gamelogic.NormalMove;
import org.mmgroup.gamelogic.OutOfBaseAntiMove;
import org.mmgroup.gamelogic.PlayerColors;

/**
 * Handles message from server and changes board or other information accordingly
 * @author Manuela.Markowska
 *
 */
public class ClientCommander {
  Game game;
  
  public ClientCommander(Game game) {
    this.game = game;
  }

  /**
   * Interprets message from server and modify client/game
   * @param client
   * @param message
   */
  public void handleMessage(Client client,String message) {
    //System.out.println(this + " CLIENT: Dostano wiadomosc od Server: " + message);
    String[] args = message.split(";");
    switch(args[0]) {
    case "printToConsole":
      System.out.println(client + ": server rozkazał printa: " + args[1]);
      break;
    case "setId":
      int newId = Integer.parseInt(args[1]);
      client.setId(newId);
      break;
    case "movePawn":
      int fromX = Integer.parseInt(args[1]);
      int fromY = Integer.parseInt(args[2]);
      int toX = Integer.parseInt(args[3]);
      int toY = Integer.parseInt(args[4]);
      game.getBoard().movePawn(fromX, fromY, toX, toY);
      if(game.getClient().getCurrentPlayersTurnId() == client.getId()) {
        game.canSelectNewPawn = false;
        game.currentPosPawnX = toX;
        game.currentPosPawnY = toY;
      }
      game.getGui().repaintBoard();
      break;
    case "createBoard":
      int x = Integer.parseInt(args[1]);
      int y = Integer.parseInt(args[2]);
      game.setBoard(new Board(x,y));
      System.out.println("ClientCommadner: Stworzono board");
      game.setGUI(new GUI(game,game.getClient()));
      break;
    case "createBoardFactory":
      int numberOfPlayers = Integer.parseInt(args[1]);
      
      BoardFactory bf = new TwoPlayersChineseCheckersFactory();
      if(numberOfPlayers==2) {
        bf = new TwoPlayersChineseCheckersFactory();
      }else if(numberOfPlayers==3) {
        //TO DO
      }else if(numberOfPlayers==4) {
        //TO DO
      }else if(numberOfPlayers==6) {
        //TO DO
      }
      game.setBoard(bf.buildBoard());
      System.out.println("ClientCommadner: Stworzono board");
      game.setGUI(new GUI(game,game.getClient()));
      break;
    case "insertPawn":
      int xpawn = Integer.parseInt(args[1]);
      int ypawn = Integer.parseInt(args[2]);
      int id = Integer.parseInt(args[3]);
      game.getBoard().insertPawn(xpawn, ypawn, id);
      game.getGui().repaintBoard();
      break;
    case "setFieldActive":
      int xField = Integer.parseInt(args[1]);
      int yField = Integer.parseInt(args[2]);
      int bool = Integer.parseInt(args[3]); //1 - true, 0 - false 
      game.getBoard().toggleActive(xField, yField, bool==1);
      game.getGui().repaintBoard();
      break;
    case "newTurn":
      int turaGracza = Integer.parseInt(args[1]);
      if(turaGracza == client.getId()) {
        client.setMyTurn(true);
        //reset zaznaczonego pionka
        game.currentPosPawnY = -1;
        game.currentPosPawnX = -1;
        game.canSelectNewPawn = true;
        game.getGui().repaintBoard();
      }else {
        client.setMyTurn(false);
      }
      client.setCurrentPlayersTurnId(turaGracza);
      //game.getGui().setKomunikat("Twoje ID: " + client.getId() + " Tura gracza o id: " + turaGracza);
      game.getGui().setBar2Color(PlayerColors.instance.getPlayerColor(turaGracza));
      game.getGui().setBarColor(PlayerColors.instance.getPlayerColor(client.getId()));
      break;
    case "addRule":
      String ruleOption = args[1];
      if(ruleOption.equals("normalMove")) {
        game.moveRules.addMoveRuleOption(new NormalMove());
      }else if(ruleOption.equals("jumpMove")) {
        game.moveRules.addMoveRuleOption(new JumpMove());
      }else if(ruleOption.equals("outOfWinAntiMove")) {
        game.moveRules.addMoveRuleOption(new OutOfBaseAntiMove());
      }
      break;
    case "forceEndTurn":
      game.currentPosPawnY = -1;
      game.currentPosPawnX = -1;
      game.getClient().sendMessage("endTurn");
      game.getGui().repaintBoard();
      break;
    }
  }
}
