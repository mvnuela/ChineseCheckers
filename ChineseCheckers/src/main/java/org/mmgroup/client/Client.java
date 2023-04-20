package org.mmgroup.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.mmgroup.gamelogic.Game;


/**
 * Client class, if thread is run, object is constantly reading messages received from server
 * class also holds basic information about connection and current game status such as whose turn is it now
 * @author Manuela.Markowska
 *
 */
public class Client implements Runnable{
  int clientId = -1;
  int currentTurnId = -1;
  Socket socket;
  PrintWriter writer;
  ClientCommander commander;
  boolean isMyTurn = false;
  
  public boolean isMyTurn() {
    return isMyTurn;
  }
  
  public void setMyTurn(boolean bool) {
    isMyTurn = bool;
  }
  
  public int getId() {
    return clientId;
  }
  
  public void setId(int clientId) {
    this.clientId = clientId;
  }
  
  public int getCurrentPlayersTurnId() {
    return currentTurnId;
  }
  
  public void setCurrentPlayersTurnId(int currentTurnId) {
    this.currentTurnId = currentTurnId;
  }
  
  /**
   * Connects to ip address with given port, and creates clientcommander and gives it a game to handle later
   * @param ipAddress
   * @param port
   * @param game
   */
  public void Connect(String ipAddress,int port,Game game){
    try {
      socket = new Socket(ipAddress, port);
      System.out.println("CLIENT: Połączono");
      commander = new ClientCommander(game);
      System.out.println("CLIENT: ClientCommander stworzony");
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      InputStream input = socket.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      
      OutputStream output = socket.getOutputStream();
      writer = new PrintWriter(output, true);
      this.sendMessage("ready");
      
      String clientMessage;
      do {
          clientMessage = reader.readLine();
          //System.out.println(this + ": Dostano wiadomosc " + clientMessage);
          commander.handleMessage(this,clientMessage);
      } while (!clientMessage.equals("bye"));
    }catch(Exception ex) {}
  }
  
  /**
   * sends given message to server
   * @param message
   */
  public void sendMessage(String message) {
    writer.println(message);
  }
}
