package org.mmgroup.UI;

import org.mmgroup.gamelogic.Board;
import org.mmgroup.gamelogic.Game;
import org.mmgroup.gamelogic.PlayerColors;
import org.mmgroup.gamelogic.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    Game game;
    Board b;
    int xPole;
    int yPole;
    UserInputInterpreter inputInterpreter;
    public GamePanel(Game game){
        //this.setBackground(Color.BLACK);
        this.game = game;
        this.b = game.getBoard();
        inputInterpreter = new UserInputInterpreter(game);
        System.out.println(b);
        this.setVisible(true);
        this.setSize(new Dimension(b.getWidth(),b.getHeight()));
        for(int i=0; i < b.getWidth(); i++)
           for(int j=0; j < b.getHeight();j++){
                b.Grid[i][j].getEllipse();
            }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point p = e.getPoint();
                for(int i = 0; i < b.getWidth(); i++)
                    for (int j = 0; j < b.getHeight(); j++)
                        if(b.Grid[i][j].getEllipse().contains(p)){
                            // i,j to wspolrzedne pola
                            //xPole = i;
                            //yPole = j;
                          clickedField(i,j);
                        }
            }
        });
    }
    //moze sie przydadzą
    public void clickedField(int x,int y) {
      inputInterpreter.handleClick(x,y);
      repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        /*
         * Sprawdzenie czy jest zaznaczony pionek, jesli tak to znajdz wszystkie mozliwe ruchy
         */
        int x = game.currentPosPawnX;
        int y = game.currentPosPawnY;
        ArrayList<Vector2> possMoves = new ArrayList<Vector2>();
        if(x != -1 && y!=-1) {
          possMoves = game.moveRules.getAvailableMovesForPos(b, x, y, !game.canSelectNewPawn);
//          for(Vector2 a:possMoves) {
//            System.out.println("dd " + a.x +" "+a.y);
//          }
        }
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < b.getWidth(); i++){
            for(int j = 0; j < b.getHeight(); j++){
                if(b.Grid[i][j].getActive()){
                    /*
                     * Ustalanie koloru
                     */
                    if(b.Grid[i][j].getPawn()!=null){ 
                        if(x==i && y==j) {
                          g2d.setColor(Color.BLACK); //zaznaczony (swój) pionek
                        }else { // Kolor innego gracza
                          Color color = PlayerColors.instance.getPlayerColor(b.Grid[i][j].getPawn().getOwnerId());
                          g2d.setColor(color);
                        }
                    }
                    else //bez pionka
                    {
                        if(arrayVector2Contains(possMoves,i,j)) {
                          g2d.setColor(Color.DARK_GRAY);
                        }else {
                          g2d.setColor(Color.LIGHT_GRAY);
                        }
                    }
                    g2d.fill(b.Grid[i][j].getEllipse());
                }
            }
        }
    }
    
    boolean arrayVector2Contains(ArrayList<Vector2> arr,int x,int y) {
      for(Vector2 vect: arr) {
        if(vect.x == x && vect.y == y) {
          return true;
        }
      }
      return false;
    }
}
