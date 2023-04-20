package org.mmgroup.boardfactory;

import org.mmgroup.gamelogic.Board;

/**
 * Factory interface to build boards
 * @author Manuela.Markowska
 *
 */
public interface BoardFactory {
  public Board buildBoard();
  public int[][] getWinCondition();
}
