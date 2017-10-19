package schocken.schocken_v1_1.player;

import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyInException;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyOutException;

/**
 * Created by marco on 14.08.17.
 */

public interface PlayerActions {

    /**
     * This method lets the shot as he is.
     * @throws Exception
     */
    void stay() throws Exception;

//    /**
//     * This method sets the block for the current player active
//     */
//    void block();
//
//    /**
//     * This method sets the class blind active.
//     * @throws Exception
//     */
//    void blind() throws Exception;

    /**
     * This method rolls the dices of the current player.
     * @return True, if the rolling was successful.
     */
    boolean rollTheDice();

    /**
     * This method takes a dice in again.
     * @param dice An object of the class dice.
     */
    boolean takeDiceInAgain(final Dice dice) throws DiceAlreadyInException;

    /**
     * This method takes a dice out of a cup.
     * @param dice An object of the class dice.
     */
    boolean takeDiceOut(final Dice dice) throws DiceAlreadyOutException;

//    /**
//     * This method turns around the dices.
//     * @throws Exception
//     */
//    void turnAround() throws Exception;

    /**
     * This method
     */
    void uncover();
}
