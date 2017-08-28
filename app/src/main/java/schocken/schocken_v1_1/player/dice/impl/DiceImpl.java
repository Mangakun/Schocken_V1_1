package schocken.schocken_v1_1.player.dice.impl;

import android.graphics.Color;
import android.support.annotation.NonNull;

import java.util.Random;

import schocken.schocken_v1_1.player.dice.Dice;

/**
 * Created by marco on 14.08.17.
 */

public class DiceImpl implements Dice{
    /**
     * The value of the dice.
     */
    private int diceValue;

    /**
     * An object of the class {@link Random}.
     */
    private Random random;

    /**
     * The color of the dice.
     */
    private Color color; // TODO: implementation ?

    /**
     * The picture which shows the number one.
     */
    private String numberOnePicture;// TODO: implementation ?

    /**
     * Constructor of the class {@link DiceImpl}.
     */
    public DiceImpl() {
        this.diceValue = 0;
        random = new Random();
    }

    /**
     * Constructor of the class {@link DiceImpl}.
     * @param diceValue The start value which should the dice have.
     */
    public DiceImpl(final int diceValue){
        this.diceValue = diceValue;
        random = new Random();
    }

    @Override
    public void roll() {
        diceValue = (random.nextInt(6) + 1);
    }

    @Override
    public int getValue() {
        return diceValue;
    }

    @Override
    public int compareTo(@NonNull final Dice another) {
        return another.getValue() - this.diceValue;
    }

    @Override
    public String toString() {
        return "DiceImpl{" +
                "diceValue=" + diceValue +
                ", random=" + random +
                '}';
    }
}
