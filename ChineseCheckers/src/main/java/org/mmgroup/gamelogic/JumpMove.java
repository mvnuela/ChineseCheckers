package org.mmgroup.gamelogic;

import java.util.ArrayList;

/**
 * Jump move - jumps over another pawn
 * @author Manuela.Markowska
 *
 */
public class JumpMove extends Move {
  public JumpMove() {
    super();
  }
  
  /**
   * Generate moves for jump move
   */
  @Override
  public ArrayList<Vector2> generateMoves(Board board,int pawnPosX,int pawnPosY,ArrayList<Vector2> possibleMoves) {
    if(possibleMoves==null) {
      possibleMoves = new ArrayList<Vector2>();
    }
    for(int i=0;i<6;i++) {
//      System.out.println("===============");
      boolean end = false;

      int stepCount = 0;
      Vector2 currStep = new Vector2(pawnPosX,pawnPosY);
//      Vector2 currStep = incrementStep(pawnPosX,pawnPosY,i);
      while(!end) {
        stepCount++;
        currStep = incrementStep(currStep.x, currStep.y, i);
        
        //Sprawdzenie czy nie wyszlo poza tablicę
        if(currStep.x < 0 || currStep.y < 0) {
          end = true;
//          System.out.println("out");
          break;
        }
        if(currStep.x > board.getWidth() || currStep.y > board.getHeight()) {
          end = true;
//          System.out.println("out");
          break;
        }
        
        //Sprawdzenie czy znajduje sie pionek do przeskoczenia
        try {
          /*
           * Znaleziono pionka po n ruchach więc wykonujemy kolejne n ruchów aby znalezc pole docelowe
           */
          if(board.getPawn(currStep.x, currStep.y)!=null) {
//            System.out.println("Znaleziono pionka na " + currStep.x +" "+ currStep.y);
            int leftSteps = stepCount;
            Vector2 target = new Vector2(currStep.x, currStep.y);
            boolean metObstacle = false;
            while(leftSteps!=0) {
              target = incrementStep(target.x,target.y,i);
              leftSteps--;
              if(board.getPawn(target.x, target.y)!=null) {
                metObstacle = true;
              }
            }
//            System.out.println("Znaleziono target na " + target.x +" "+ target.y);
            /*
             * Sprawdzenie czy pole docelowe jest wolne
             */
            if(board.getPawn(target.x, target.y)==null && !metObstacle) {
              possibleMoves.add(target);
            }
            end = true;
          }
          
        }catch(ArrayIndexOutOfBoundsException ex) {}
        //possibleMoves.add(currStep);
        
      }
    }
    
    return possibleMoves;
  }
  
  /**
   * Makes one step towards i direction
   * @param x
   * @param y
   * @param i
   * @return
   */
  Vector2 incrementStep(int x,int y,int i) {
    if(y%2==0) {
      x = x + directionsEven.get(i).x;
      y = y + directionsEven.get(i).y;
    }else {
      x = x + directionsOdd.get(i).x;
      y = y + directionsOdd.get(i).y;
    }
    return new Vector2(x,y);
    
  }
}
