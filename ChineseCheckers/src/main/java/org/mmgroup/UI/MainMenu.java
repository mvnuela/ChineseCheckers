package org.mmgroup.UI;

import javax.swing.*;

import org.mmgroup.gamelogic.Game;
import org.mmgroup.server.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    protected String readNick;
    protected String port;
    JButton connectButton = new JButton("Polacz");
    JButton serwerButton = new JButton("Stwórz serwer");
    JPanel panel = new JPanel();
    JTextField nickArea = new JTextField("Tutaj wprowadź nick(not implemented)",20);
    JTextField portArea = new JTextField("6666",20);
    JTextField IParea = new JTextField("localhost",20);
    String[] playerCountOptions = {"2 Players", "3 Players", "4 Players", "6 Players" };
    JComboBox<String> playerCount = new JComboBox<String>(playerCountOptions);
    public MainMenu(){
        this.add(panel);
        this.setBounds(50,30,300,200);
        setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.add(connectButton);
        panel.add(IParea);
        panel.add(portArea);
        panel.add(playerCount);
        panel.add(serwerButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                  int port = Integer.parseInt(portArea.getText());
                  new Game(IParea.getText(),port);
                }catch(Exception ex) {
                  System.out.println("Blad polaczenia");
                }
            }
        });
        serwerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              try {
                int numberPlayers = 2;
                
                if(playerCount.getSelectedItem()=="3 Players") {
                  numberPlayers = 2;
                }else if(playerCount.getSelectedItem()=="4 Players") {
                  numberPlayers = 4;
                }else if(playerCount.getSelectedItem()=="6 Players") {
                  numberPlayers = 6;
                }
                
                int port = Integer.parseInt(portArea.getText());
                Server server = new Server(port);
                /*
                 * Ustawianie opcji
                 */
                server.getGameLobby().addMoveRule("normalMove");
                server.getGameLobby().addMoveRule("jumpMove");
                server.getGameLobby().addMoveRule("outOfWinAntiMove");
                
                server.setNumberOfPlayers(numberPlayers);
                Thread serverThread = new Thread(server);
                serverThread.start();
              }catch(Exception ex) {
                System.out.println("Blad podczas tworzenia serwera. Port nie jest liczba?. Stworzono juz serwer o tym porcie?");
              }
            }
        });


    }

}
