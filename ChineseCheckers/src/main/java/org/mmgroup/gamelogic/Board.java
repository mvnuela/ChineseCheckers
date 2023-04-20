package org.mmgroup.gamelogic;

import javax.swing.*;
import java.awt.*;

public class Board{
  public Field[][] Grid;
  int width;
  int height;
  public int[][] winCondition;
  
  public Board(int width, int height) {
    this.height = height;
    this.width = width;
    Grid = new Field[width][height];
    for(int i=0; i < width;i++){
      for(int j=0;j<height;j++)
        Grid[i][j]=new Field(i,j);
    }
  }
 public boolean insertPawn(int x,int y,int playerId) {
    if(Grid[x][y].isOccupied()) {
      return false;
    }
    Grid[x][y].setPawn(new Pawn(playerId));
    return true;
  }
  public boolean movePawn(int fromX,int fromY,int toX,int toY) {
    if(!Grid[fromX][fromY].isOccupied() || Grid[toX][toY].isOccupied())
      return false;
    Pawn pawn = Grid[fromX][fromY].getPawn();
    Grid[fromX][fromY].removePawn();
    Grid[toX][toY].setPawn(pawn);
    return true;
  }
  public Pawn getPawn(int x,int y) {
    return Grid[x][y].getPawn();
  }
  
  public void removePawn(int x,int y) {
    Grid[x][y].removePawn();
  }
  public int getWidth(){
    return width;
  }
  public int getHeight(){
    return height;
  }
  public void toggleActive(int x,int y,boolean bool) {
    this.Grid[x][y].setActive(bool);
  }
}
