package org.mmgroup.server;

import java.io.*;
import java.net.Socket;
/**
 * ConnectedPlayer czyta wiadomości wysłane przez klienta dla którego został utworzony ten obiekt/thread
 * Można również wysylac wiadomosci do klienta przez metode send message
 */
public class ConnectedPlayer implements Runnable{
  int playerId = -1;
  String playerName = "DEFAULT";
  Socket socket;
  PrintWriter writer;
  Server server;
  ServerCommander serverCommander;
  boolean ready = false;
  boolean itsTurn = false;
  boolean playingStatus = true;

  public boolean movedThisTurn = false;
  
  public boolean getPlayingStatus() {
    return playingStatus;
  }
  
  public void setPlayingStatus(boolean playingStatus) {
    this.playingStatus = playingStatus;
  }
  
  public int getId() {
    return playerId;
  }
  
  public void setId(int id) {
    this.playerId = id;
  }
  
  public void setTurn(boolean bool) {
    this.itsTurn = bool;
  }
  
  public boolean isItsTurn() {
    return itsTurn;
  }
  
  public ConnectedPlayer(Socket socket,Server server,ServerCommander serverCommander) {
    this.socket = socket;
    this.server = server;
    this.serverCommander = serverCommander;
  }
  
  public boolean getReady() {
    return ready;
  }
  
  public void setReady() {
    ready = true;
  }
  
  public void setUnReady() {
    ready = false;
  }
  
  public String getPlayerName() {
    return playerName;
  }
  
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public void run() {
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
      
      String clientMessage;
      do {
          //System.out.println("SERVER: Czekanie na wiadomosc");
          clientMessage = reader.readLine();
          //serverMessage = clientMessage;
          serverCommander.handleMessage(this, clientMessage);
          //server.broadcast(serverMessage, this);
  
      } while (!clientMessage.equals("Disconnect"));
    }catch(Exception ex) {}
    
  }
  
  /**
   * Sends message to server
   * @param message
   */
  public void sendMessage(String message) {
    writer.println(message);
  }
}
