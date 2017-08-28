package schocken.schocken_v1_1.view.gameview;

import java.util.List;

import schocken.schocken_v1_1.player.Player;

/**
 * Created by marco on 25.08.17.
 */

public interface GameView {

   /**
    * This method add players to the view.
    * @param players
     */
   void addPlayers(final List<Player> players);
}
