package schocken.schocken_v1_1.view.playerview;

import java.util.List;

import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.dice.Dice;

/**
 * Created by marco on 25.08.17.
 */

public interface PlayerView {

    /**
     * This method enables the roll the dice button.
     */
    void enableRollTheDiceButton();

    /**
     * This method disables the roll the dice button.
     */
    void disableRollTheDiceButton();

    /**
     * This method enables the blind button.
     */
    void enableBlindButton();

    /**
     * This method disables the blind button.
     */
    void disableBlindButton();

    /**
     * This method enables the unvover button.
     */
    void enableUncoverButton();

    /**
     * This method disables the uncover button.
     */
    void disableUncoverButton();

    /**
     * This method enabls the stay button.
     */
    void enableStayButton();

    /**
     * This method disables the stay button.
     */
    void disableStayButton();

    /**
     * This method sets the current player in the player view.
     * @param currentPlayer An object of the class {@link Player}.
     */
    void setCurrentPlayer(final Player currentPlayer);

    /**
     * This method updates the player view and gets the new player data
     */
    void updatePlayerInfo();

    //void setTournAroundOption();
}
