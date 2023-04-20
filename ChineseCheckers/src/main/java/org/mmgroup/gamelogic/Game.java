package org.mmgroup.gamelogic;

import org.mmgroup.UI.GUI;
import org.mmgroup.client.Client;

public class Game {
  Board board;
  Client client;
  Thread clientThread;
  GUI gui;
  
  public GameRules moveRules;
  public boolean canSelectNewPawn = true;
  public int currentPosPawnX = -1;
  public int currentPosPawnY = -1;
  
  public Game(String address,int port) {
    new PlayerColors();
    moveRules = new GameRules();
    
    client = new Client();
    client.Connect(address, port, this);
    
    clientThread = new Thread(client);
    clientThread.start();
  }
  
  public void setGUI(GUI gui) {
    this.gui = gui;
  }
  
  public GUI getGui() {
    return gui;
  }
  
  public Board getBoard() {
    return board;
  }
  
  public void setBoard(Board board) {
    this.board = board;
  }
  
  public Client getClient() {
    return client;
  }
}
