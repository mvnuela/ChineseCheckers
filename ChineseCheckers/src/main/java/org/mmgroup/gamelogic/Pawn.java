package org.mmgroup.gamelogic;

/**
 * Pawn object holds ownerId
 * @author Manuela.Markowska
 *
 */
public class Pawn {
  int ownerID;
  public Pawn(int ownerId) {
    this.ownerID = ownerId;
  }
  
  public int getOwnerId() {
    return ownerID;
  }
}
