package org.mmgroup.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.mmgroup.gamelogic.Board;
/**
 * Server class, holds basic connectios info
 * 
 */
public class Server implements Runnable {
  ServerSocket serverSocket;
  int port = 30120;
  int numberOfPlayers = 2;
  GameLobby gameLobby;
  
  List<ConnectedPlayer> connectedPlayers = new ArrayList<ConnectedPlayer>();
  List<Thread> connectedPlayersThreads = new ArrayList<Thread>();
  
  public Server(int port) {
    this.port = port;
    gameLobby = new GameLobby(this);
  }
  
  public GameLobby getGameLobby() {
    return gameLobby;
  }
  
  public void setNumberOfPlayers(int numberOfPlayers) {
    this.numberOfPlayers = numberOfPlayers;
  }
  
  public List<ConnectedPlayer> getAllPlayers() {
    return connectedPlayers;
  }
  
  public int getNumberOfPlayers() {
    return connectedPlayers.size();
  }
  
  public ConnectedPlayer getConnectedPlayerById(int playerId) {
    return connectedPlayers.get(playerId);
  }
  
  /**
   * server waits for required connections then starts the game
   */
  @Override
  public void run() {
    try {
      System.out.println("SERVER: Creating commander: ");

      ServerCommander serverCommander = new ServerCommander(gameLobby,this);
      
      System.out.println("SERVER: Creating commander SUCCESS: ");
      System.out.println("SERVER: Trying to host on port: " + port);
      serverSocket = new ServerSocket(port);
      System.out.println("SERVER: Hosted: ");
      //Dodaje graczy i tworzy dla nich connectedPlayers;
      for(int i=0;i<numberOfPlayers;i++) {
        System.out.println("SERVER: Czekanie na gracza...");
        Socket socket = serverSocket.accept();
        System.out.println("SERVER: Gracz polaczony...");
        ConnectedPlayer cp = new ConnectedPlayer(socket,this,serverCommander);
        connectedPlayers.add(cp);
        Thread t = new Thread(cp);
        t.start();
        connectedPlayersThreads.add(t);
        
        System.out.println("SERVER: Dolaczyl gracz. Do rozpoczecia rozgrywki potrzeba jeszcze: " + (numberOfPlayers-getNumberOfPlayers()));
      }
      //Oczekiwanie aż każdy gracz się załaduje (aby można było do niego wysłac wiadomosc)
      boolean czyStart=false;
      while(!czyStart) {
        czyStart = true;
        for(int i=0;i<numberOfPlayers;i++) {
          if(!connectedPlayers.get(i).getReady()) {
            czyStart=false;
          }
        }
      }
      //Wysylanie kazdemu graczowi jego id
      for(int i=0;i<numberOfPlayers;i++) {
        String mess = "setId;"+i;
        connectedPlayers.get(i).sendMessage(mess);
        connectedPlayers.get(i).setId(i);
      }
      
      System.out.println("SERVER: Startowanie rozgrywki");
      gameLobby.startGame();
      /*
       * Serwer powinien zrobic boarda o x,y i przeslac wiadomosc, nastepnie wstawic pionki w odpowiednie miejsce oraz oflagowac
       * pola ktore nie sa aktywne w grze.
       */
      
      //this.broadcast("createBoard;20;20");
      //this.broadcast("insertPawn;1;1;1");
    }catch(Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
  /**
   * Sends message to all players
   */
  public void broadcast(String message) {
    for(ConnectedPlayer player: connectedPlayers) {
      player.sendMessage(message);
    }
  }
}
