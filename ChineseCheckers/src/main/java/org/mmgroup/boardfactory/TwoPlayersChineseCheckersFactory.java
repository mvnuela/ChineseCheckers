package org.mmgroup.boardfactory;

import org.mmgroup.gamelogic.Board;

/**
 * Factory for chinese checkers for two players
 * @author Manuela Markowska
 *
 */
public class TwoPlayersChineseCheckersFactory implements BoardFactory {

  public Board buildBoard() {
    return twoPlayerGame();
  }
  /**
   * Grid with information how to build board
   * n=0 - field inactive
   * n=1 - field active
   * n>2 - field contains pawn with id n-2
   */
  int[][] twoGrid = {
      {0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0},
       {0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0},
      {0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0},
       {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
       {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
      {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
       {0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
      {0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0},
       {0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
      {0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
       {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
      {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
       {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
      {0,0,0,0,0,0,3,3,3,3,2,0,0,0,0,0},
       {0,0,0,0,0,0,3,3,3,3,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,3,3,3,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0},
  };
  
  int[][] winCondition = {
      {0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,3,3,3,0,0,0,0,0,0},
       {0,0,0,0,0,0,3,3,3,3,0,0,0,0,0,0},
      {0,0,0,0,0,0,3,3,3,3,3,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,2,2,2,2,2,0,0,0,0,0},
       {0,0,0,0,0,0,2,2,2,2,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0},
       {0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0},
      {0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0},
  };
  
  /**
   * Grids interpreter, builds based on twoGrid and winCondition
   * @return board
   */
  public Board twoPlayerGame() {
    Board board = new Board(twoGrid[0].length,twoGrid.length);
    for(int i=0;i<twoGrid.length;i++) {
      for(int j=0;j<twoGrid[i].length;j++) {
          int number = twoGrid[i][j];
          if(number == 0) {
            board.Grid[j][i].setActive(false);
          }else if(number == 1) {
            board.Grid[j][i].setActive(true);
          }else {
            board.insertPawn(j, i, number-2);
          }
      }
    }
    board.winCondition = this.getWinCondition();
    return board;
  }
  
  /**
   * returns winCondition array
   */
  public int[][] getWinCondition() {
    return winCondition;
  }
  
}
