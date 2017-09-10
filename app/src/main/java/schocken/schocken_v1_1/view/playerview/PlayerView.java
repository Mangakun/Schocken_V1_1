package schocken.schocken_v1_1.view.playerview;

import java.util.List;

import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.dice.Dice;

/**
 * Created by marco on 25.08.17.
 */

public interface PlayerView {



    void clearPlayerView();

    void enableRollTheDiceButton();

    void disableRollTheDiceButton();

    void enableBlindButton();

    void disableBlindButton();

    void enableUncoverButton();

    void disableUncoverButton();

    void enableStayButton();

    void disableStayButton();


    void uncoverDice();


    void setCurrentPlayer(final Player currentPlayer);


    void updatePlayerInfo();

    //void setTournAroundOption();
}
