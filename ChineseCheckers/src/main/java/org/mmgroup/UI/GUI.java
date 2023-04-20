package org.mmgroup.UI;

import javax.swing.*;

import org.mmgroup.client.Client;
import org.mmgroup.gamelogic.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    GamePanel panel;
    Client client;
    JButton button = new JButton("Zakończ ture");
    JMenuBar bar = new JMenuBar();
    JMenuBar bar2 = new JMenuBar();
    JLabel label = new JLabel("Twoj kolor");
    JLabel label2 = new JLabel("Gracz przy ruchu");
    public GUI(Game game,Client client){
        this.panel = new GamePanel(game);
        this.client = client;
        bar.setLayout(new GridLayout(1,2));
        this.setJMenuBar(bar);
        bar.add(label);

        label.setFont(new Font(label2.getName(), Font.PLAIN, 20));
        label2.setFont(new Font(label2.getName(), Font.PLAIN, 20));
        bar2.add(label2);
        bar.add(bar2);
        bar.add(button);
        this.setVisible(true);
        this.setSize(new Dimension(800,850));
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              endTurnPressed();
            }
        });
    }
    public void endTurnPressed() {
      Game game = this.panel.game;
      game.currentPosPawnY = -1;
      game.currentPosPawnX = -1;
      game.canSelectNewPawn = true;
      game.getGui().repaintBoard();
      this.client.sendMessage("endTurn");
    }
    //może sie przyda
    public void setKomunikat(String komunikat){
        this.label.setText(komunikat);
    }
    public void setBarColor(Color color) {
        this.bar.setBackground(color);
    }
    public void setBar2Color(Color color) {
      this.bar2.setBackground(color);
    }
    public void repaintBoard() {
      this.panel.repaint();
    }
}
