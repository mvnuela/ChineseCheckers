package org.mmgroup.gamelogic;
/**
 * Stores move vector
 * @author Manuela.Markowska
 *
 */
public class Vector2 {
  public int x;
  public int y;
  /**
   * If set to true by Move object the turn will automatically end after this move vector has been performed
   */
  public boolean forceTurnAfterThisMove = false;
  
  public Vector2(int x,int y) {
    this.x = x;
    this.y = y;
  }
}
