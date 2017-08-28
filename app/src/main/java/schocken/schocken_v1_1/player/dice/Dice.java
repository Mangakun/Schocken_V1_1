package schocken.schocken_v1_1.player.dice;

/**
 * Created by marco on 14.08.17.
 */

public interface Dice extends Comparable<Dice>{

    /**
     * This method rolls the dice.
     */
    void roll();

    /**
     * This method returns the value of the dice.
     * @return The dice value.
     */
    int getValue();
}
