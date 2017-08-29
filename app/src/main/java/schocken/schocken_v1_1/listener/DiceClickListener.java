package schocken.schocken_v1_1.listener;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyInException;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyOutException;


/**
 * This class is an extension of the View.OnClickListener.
 * Created by marco on 04.05.17.
 */

public class DiceClickListener implements View.OnClickListener {

    /**
     * A final string for debug.
     */
    private final String msg = "DiceClickListener";

    /**
     * An object of the class {@link Player}
     */
    private Player player;

    /**
     * The dice value.
     */
    private Dice dice;

    /**
     * A boolean which holds if a dice should be taken out or not.
     */
    private boolean shouldBeTakenOut;

    /**
     * Constructor of the class {@link DiceClickListener}.
     * @param player An object of the class {@link Player}.
     * @param dice An object of the class {@link Dice}.
     */
    public DiceClickListener(final Player player, final Dice dice){
        this.player = player;
        this.dice = dice;
        shouldBeTakenOut = false;

    }
    @Override
    public void onClick(final View view) {
        //
        if(shouldBeTakenOut){
            Log.d(msg, "onClick -> take in again");
            try {
                player.takeDiceInAgain(dice);
                view.setBackgroundColor(0);
            } catch (DiceAlreadyInException e) {
                e.printStackTrace();
            }
        }else{
            Log.d(msg, "onClick -> take out");
            try {
                player.takeDiceOut(dice);
                view.setBackgroundColor(Color.BLUE);
            } catch (DiceAlreadyOutException e) {
                e.printStackTrace();
            }
        }
        // invert the boolean
        shouldBeTakenOut = !shouldBeTakenOut;

    }
}
