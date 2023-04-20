package org.mmgroup.gamelogic;

import java.util.ArrayList;

/**
 * Class to hold rules and apply them
 * @author Manuela.Markowska
 *
 */
public class GameRules {
  ArrayList<Move> availableMoves = new ArrayList<Move>();
  
  /**
   * Adds new move rule
   * @param move
   */
  public void addMoveRuleOption(Move move) {
    availableMoves.add(move);
  }
  
  /**
   * Return possible moves for given board and position
   * @param board
   * @param x
   * @param y
   * @param movedInThisTurn
   * @return
   */
  public ArrayList<Vector2> getAvailableMovesForPos(Board board, int x, int y,boolean movedInThisTurn){
    ArrayList<Vector2> outcome = new ArrayList<Vector2>();
    for(int i=0;i<availableMoves.size();i++) {
      Move option = availableMoves.get(i);
      if(option instanceof NormalMove && movedInThisTurn) {
//        System.out.println("Pomija ruch");
      }else {
        option.generateMoves(board, x, y, outcome);

      }
    }
    return outcome;
  }
}
