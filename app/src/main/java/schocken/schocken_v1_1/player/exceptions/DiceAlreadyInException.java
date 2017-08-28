package schocken.schocken_v1_1.player.exceptions;

/**
 * Created by Marco on 23.03.2017.
 */


import schocken.schocken_v1_1.player.dice.Dice;

/**
 * An exception class for not founding a dice.
 */
public class DiceAlreadyInException extends Exception {

    /**
     * Constructor of the class {@link DiceAlreadyInException}.
     * @param dice An object of te class {@link Dice}.
     */
    public DiceAlreadyInException(final Dice dice){
        super("The dice "+dice.getValue()+" is already in!");
    }
}
