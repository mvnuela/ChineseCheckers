package org.mmgroup.gamelogic;

import java.util.ArrayList;

public class NormalMove extends Move{
  
  public NormalMove() {
    super();
  }
/**
 * Generates moves for walk/normal move by 1 hexgrid
 */
  @Override
  public ArrayList<Vector2> generateMoves(Board board,int pawnPosX,int pawnPosY,ArrayList<Vector2> possibleMoves) {
    if(possibleMoves==null) {
      possibleMoves = new ArrayList<Vector2>();
    }
    for(int i=0;i<6;i++) {
      int currX;
      int currY;
      if(pawnPosY%2==0) {
        currX = pawnPosX + directionsEven.get(i).x;
        currY = pawnPosY + directionsEven.get(i).y;
      }else {
        currX = pawnPosX + directionsOdd.get(i).x;
        currY = pawnPosY + directionsOdd.get(i).y;
      }
      
      try {
        if(board.Grid[currX][currY].getActive() && board.getPawn(currX, currY)==null) {
          Vector2 vector2 = new Vector2(currX,currY);
          vector2.forceTurnAfterThisMove = true;
          possibleMoves.add(vector2);
        }
      }catch(ArrayIndexOutOfBoundsException ex) {}
    }
    return possibleMoves;
  }
}
