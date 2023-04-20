package org.mmgroup.gamelogic;

import java.util.ArrayList;
/**
 * Out of base antimove
 * @author Manuela.Markowska
 *
 */
public class OutOfBaseAntiMove extends Move{
  public OutOfBaseAntiMove() {
    super();
  }
  /**
   * Modify given possibleMoves and removes those which violates the given rule, the pawn cannot leave its destination/home base/win condition
   */
  @Override
  public ArrayList<Vector2> generateMoves(Board board,int pawnPosX,int pawnPosY,ArrayList<Vector2> possibleMoves) {
    try {
      ArrayList<Vector2> toDelete = new ArrayList<Vector2>();
      
      if(possibleMoves==null) {
        return null; //nie ma co usunac z mozliwych ruchow
      }
      //Sprawdza czy pionek jest w swoim win condition
      if(board.winCondition[pawnPosY][pawnPosX]-2 != board.getPawn(pawnPosX, pawnPosY).getOwnerId()) {
        return possibleMoves;
      }
      System.out.println("To jest ten ruch");
      //Bierze ruchy i sprawdza czy wyszlo z poza win condition, jesli tak to go usuwa
        for(Vector2 vect: possibleMoves) {
          //System.out.println("Sprawdzenie" + board.winCondition[vect.y][vect.x]);
          if( board.winCondition[vect.y][vect.x]-2 != board.getPawn(pawnPosX, pawnPosY).getOwnerId()){
            toDelete.add(vect);
            
          }
        }
        for(Vector2 vect: toDelete) {
          possibleMoves.remove(vect);
        }
        
    }catch(Exception ex) {}
    return possibleMoves;
  }
}
