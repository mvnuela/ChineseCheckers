package org.mmgroup.UI;

import org.mmgroup.gamelogic.Game;
import org.mmgroup.gamelogic.Pawn;
/**
 * Classthat interprets user input on GUI
 * @author Manuela.Markowska
 *
 */
public class UserInputInterpreter {
  Game game;
  
  public UserInputInterpreter(Game game) {
    this.game = game;
  }
  
  /**
   * Handles click on field
   * @param clickedX
   * @param clickedY
   */
  public void handleClick(int clickedX,int clickedY) {
    System.out.println("Klikniecie w punkt " + clickedX + " " + clickedY + " " + (game.getBoard().winCondition[clickedY][clickedX]-2));
    //Jesli jest pionek zaznacz go
    Pawn pawn = game.getBoard().getPawn(clickedX, clickedY);
    if(pawn != null) {
      if(pawn.getOwnerId()==game.getClient().getId() && game.canSelectNewPawn) {
        //System.out.println("Zaznaczono pionek " + clickedX + " " + clickedY);
        game.currentPosPawnX = clickedX;
        game.currentPosPawnY = clickedY;
        return;
      }
    // Sprobuj przesunac
    }else {
      if(game.currentPosPawnX!=-1 && game.currentPosPawnY!=-1) {
        game.getClient().sendMessage("movePawn;"+game.currentPosPawnX+";"+game.currentPosPawnY+";"+clickedX+";"+clickedY);
      }
    }
  }
}
