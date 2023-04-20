package org.mmgroup.gamelogic;

import java.util.ArrayList;

public class Move {
  
  protected ArrayList<Vector2> directionsEven = new ArrayList<Vector2>();
  protected ArrayList<Vector2> directionsOdd = new ArrayList<Vector2>();
  
  
  /**
   * move vector is different on even and odd y-positions so we store them both and move accordingly
   */
  public Move() {
    directionsEven.add(new Vector2(-1,-1));
    directionsOdd.add(new Vector2(0,-1));
    
    directionsEven.add(new Vector2(-1,0));
    directionsOdd.add(new Vector2(-1,0));
    
    directionsEven.add(new Vector2(0,1));
    directionsOdd.add(new Vector2(1,1));
    
    directionsEven.add(new Vector2(1,0));
    directionsOdd.add(new Vector2(1,0));
    
    directionsEven.add(new Vector2(-1,1));
    directionsOdd.add(new Vector2(0,1));
    
    directionsEven.add(new Vector2(0,-1));
    directionsOdd.add(new Vector2(1,-1));
  }
  
  /**
   * Generate move return empty arrayList<Vector2>
   * @param board
   * @param pawnPosX
   * @param pawnPosY
   * @param possibleMoves
   * @return
   */
  public ArrayList<Vector2> generateMoves(Board board,int pawnPosX,int pawnPosY,ArrayList<Vector2> possibleMoves) {
    if(possibleMoves==null) {
      possibleMoves = new ArrayList<Vector2>();
    }
    return possibleMoves;
  }
}
