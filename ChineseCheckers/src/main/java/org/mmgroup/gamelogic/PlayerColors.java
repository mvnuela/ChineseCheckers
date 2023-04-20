package org.mmgroup.gamelogic;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
/**
 * Holds Map that holds colors for every playerId
 * @author Manuela.Markowska
 *
 */
public class PlayerColors {
  public static PlayerColors instance;
  Map<Integer, Color> mapa;
  
  public PlayerColors(){
    instance = this;
    mapa = new HashMap<Integer, Color>();
    mapa.put(0, Color.red);
    mapa.put(1, Color.blue);
    mapa.put(2, Color.yellow);
    mapa.put(3, Color.green);
    mapa.put(4, Color.orange);
    mapa.put(5, Color.pink);
    mapa.put(6, Color.cyan);
  }
  
  public Color getPlayerColor(int id) {
    return mapa.get(id);
  }
}
