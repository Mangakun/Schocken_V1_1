package schocken.schocken_v1_1.player;

import java.util.List;

import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.player.exceptions.MaxPenaltyException;

/**
 * Created by marco on 14.08.17.
 */

public interface Player extends PlayerActions{

    /**
     * This method return the name of the player.
     * @return The name of the player.
     */
    String getName();

    /**
     * This method starts the turn of the player.
     * @param startPlayer Boolean, if the player is the start player of the round.
     */
    void myTurn(final boolean startPlayer);

    /**
     * This method returns the dice value.
     * The dice value is expanded by a multiplier and the count of penalties.
     * @return The dice value.
     */
    int getDiceValueForCompare();

    /**
     * This method returns the penalties of the dice value.
     * @return The penlaties of the dice value.
     */
    int getPenaltiesOfDiceValue();

    /**
     * This method add penalties.
     * @param penalties The penalties.
     */
    void addPenalties(final int penalties) throws MaxPenaltyException;

    /**
     * This method sets the penalties.
     * @param penalties The penalties.
     */
    void setPenalties(final int penalties) throws MaxPenaltyException;

    /**
     * This method returns the penalties of the player.
     * @return The penalties of the player.
     */
    int getPenalties();

    /**
     * This method returns the state of a player, if the is playing or if he has finished the current round.
     * @return True, if the player is finished.
     */
    boolean isFinished();

//    /**
//     * This method returns if the player called block.
//     * @return True, if the player called block.
//     */
//    boolean isBlocked();

    /**
     * This method returns the current shots of the player.
     * @return The current shots of the player.
     */
    int getCurrentShots();


    /**
     * This method returns the dices which are out.
     * @return The dices which are out.
     */
    List<Dice> getDicesOut();

    List<Dice> getDicesIn();

    /**
     * This method resets the player.
     */
    void reset();

//    /**
//     * This method returns the dices which are under the cup.
//     * @return The dices which are under the cup.
//     */
//    List<Dice> getDicesUnderTheCup();

//    /**
//     * TODO: documentation.
//     * @return
//     */
//     boolean hasFirstHalf();
//
//    /**
//     * TODO: documentation.
//     * @return
//     */
//    boolean hasSecondHalf();
    //void setFirstHalf(final boolean firstHalf);

    //void setSecondHalf(final boolean secondHalf);



}
