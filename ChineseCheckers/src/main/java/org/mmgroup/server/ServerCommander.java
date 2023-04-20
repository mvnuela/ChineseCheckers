package org.mmgroup.server;
/**
 * Klasa interpretująca wiadomości wysłane przez klientów
 */
public class ServerCommander {
  GameLobby gameLobby;
  Server server;
  
  public ServerCommander(GameLobby gameLobby,Server server) {
    this.gameLobby = gameLobby;
    this.server = server;
  }
  /**
   * connectedPlayer - gracz ktory wyslal wiadomosc
   * message - tresc wiadomosci
   * Schemat wiadomosci:
   * KOMENDA=ARGS[0];ARG1;ARG2;ARG3;...;ARGn; - wywoluje komende przy podanych argumentach.(Patrz pierwszy przyklad)
   */
  public void handleMessage(ConnectedPlayer connectedPlayer,String message) {
    //System.out.println("SERVER: Dostano wiadomosc '" + message + "' od gracza " + connectedPlayer.getId());
    String[] args = message.split(";");
    switch(args[0]) {
    case "changeNick":
      connectedPlayer.setPlayerName(args[1]);
      break;
    case "ready":
      connectedPlayer.setReady();
      break;
    case "printToConsole":
      System.out.println(args[1]);
      break;
    case "movePawn":
      if(!connectedPlayer.isItsTurn()) {
        break;
      }
      int fromX = Integer.parseInt(args[1]);
      int fromY = Integer.parseInt(args[2]);
      int toX = Integer.parseInt(args[3]);
      int toY = Integer.parseInt(args[4]);
      /*
       * Sprawdzic czy to samo id oraz czy ruch jest legalny i czy jest gracza tura
       */
      if(!gameLobby.getBoard().Grid[toX][toY].getActive()) {
        break;
      }
      if(gameLobby.getBoard().getPawn(fromX, fromY).getOwnerId() != connectedPlayer.getId()) {
        break;
      }
      //Sprawdzenie czy legalny
      int legalResult = gameLobby.checkIfMoveIsLegal(fromX, fromY, toX, toY,connectedPlayer.movedThisTurn);
      if(legalResult==2) {
        gameLobby.getBoard().movePawn(fromX, fromY, toX, toY);
        server.broadcast(message);
        connectedPlayer.movedThisTurn = true;
        connectedPlayer.sendMessage("forceEndTurn");
      }else if(legalResult==1) {
        gameLobby.getBoard().movePawn(fromX, fromY, toX, toY);
        server.broadcast(message);
        connectedPlayer.movedThisTurn = true;
      }
      break;
    case "endTurn":
      connectedPlayer.setTurn(false);
      break;
    }
  }
}
