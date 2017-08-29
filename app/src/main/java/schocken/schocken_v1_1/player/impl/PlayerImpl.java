package schocken.schocken_v1_1.player.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import schocken.schocken_v1_1.gameobserver.GameObserver;
import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.PlayerActions;
import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.player.dice.impl.DiceImpl;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyInException;
import schocken.schocken_v1_1.player.exceptions.DiceAlreadyOutException;
import schocken.schocken_v1_1.player.exceptions.MaxPenaltyException;
import schocken.schocken_v1_1.presenter.playerpresenter.PlayerPresenter;
import schocken.schocken_v1_1.view.playerview.PlayerView;

/**
 * Created by marco on 14.08.17.
 */

public class PlayerImpl implements Player {

    /**
     * A msg string for debug.
     */
    private final String debugMSG = "Player";

    /**
     * The name of the player.
     */
    private final String name;

    /**
     * A boolean which holds the state of a player.
     * Is the player playing or has he finished the round.
     */
    private boolean finish;

    /**
     *
     */
    private boolean firstHalf;


    private boolean secondHalf;

    /**
     * A list of dices which set the player out.
     */
    private List<Dice> dicesOut;

    /**
     * A list of dices which has the player under the cup.
     */
    private List<Dice> dicesIn;

    /**
     * The penalties the player has.
     */
    private int penalties;


    private boolean block;

    /**
     * The shots of the player.
     */
    private int currentShots;


    /**
     * An object of the class {@link GameObserver}.
     */
    private final GameObserver gameObserver;

    /**
     * Is this player the start player of the round ?
     */
    private boolean isStartPlayer;

    /**
     *
     */
    private PlayerView playerView;


    /**
     * Constructor of the class {@link PlayerImpl}.
     * @param name The name of the player.
     * @param gameObserver An object of the class {@link GameObserver};
     */
    public PlayerImpl(final String name, @NonNull final GameObserver gameObserver, @NonNull final PlayerView playerView){
        this.name = name;
        this.gameObserver = gameObserver;
        this.playerView = playerView;
        dicesOut = new ArrayList<>(3); // TODO: Anzahl in Klasse "Settings" verschieben
        dicesIn = new ArrayList<>(3); // TODO: Anzahl in Klasse "Settings" verschieben
        // fill dice in
        for (int i=0; i<3 ;++i){// TODO: Anzahl in Klasse "Settings" verschieben
            dicesIn.add(new DiceImpl());
        }
        finish = false;
        firstHalf = false;
        secondHalf = false;
        block = false;
        currentShots = 0;
        isStartPlayer = false;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void myTurn(final boolean startPlayer) {
        /*
        1. clear GUI
        2. create possibilities
         */
        // save, if this player is the start player of the round
        isStartPlayer = startPlayer;
        // clear player view
        playerView.clearPlayerView();
        // connect the view with this player
        playerView.setCurrentPlayer(this);
        // create now the possibilities he has
        createPossibilities();

    }

    @Override
    public int getDiceValue() {
        Collections.sort(dicesOut);
        double sum = 0;
        for(int i=dicesOut.size()-1; i >= 0;--i){
            sum += dicesOut.get(i).getValue()* Math.pow(10,i);
        }
        return (int)sum;
    }

    @Override
    public void addPenalties(int penalties) throws MaxPenaltyException {
        this.penalties+=penalties;
        if(this.penalties > 13){ // TODO: max penalties nach "settings" verschieben
            throw new MaxPenaltyException("The penalties cant be more than "+13+"!");// TODO: max penalties nach "settings" verschieben
        }

    }

    @Override
    public void setPenalties(int penalties) throws MaxPenaltyException {
        if( penalties > 13){ // TODO: max penalties nach "settings" verschieben
            throw new MaxPenaltyException("The value of the parameter penalties cant be more than "+13+"!"); // TODO: max penalties nach "settings" verschieben
        }else{
            this.penalties = penalties;
        }
    }

    @Override
    public int getPenalties() {
        return penalties;
    }

    @Override
    public boolean isFinished() {
        return finish;
    }

    @Override
    public boolean isBlocked() {
        return block;
    }

    @Override
    public int getCurrentShots() {
        return currentShots;
    }

    @Override
    public List<Dice> getDicesOut() {
        return dicesOut;
    }

    @Override
    public List<Dice> getDicesUnderTheCup() {
        return dicesIn;
    }

    @Override
    public void stay() throws Exception {
        if(currentShots > 2 || isFinished()){
            throw new Exception("The player cant not call \"stay\" ");
        }
        finish = true;
        /*
        game observer next player.
         */
    }

    @Override
    public void block() {
        block = true;
        finish = true;
        /*
        game observer next player.
         */

    }

    @Override
    public void blind() throws Exception {
        if(currentShots > 0){
            throw new Exception("The player cant not call \"blind\" ");
        }
        finish = true;
        //
        //blind call
        //game obsever next player
        //
    }

    @Override
    public boolean rollTheDice() {
        Log.d(debugMSG, "roll the dice for "+name);
        if(!finish){
            Log.d(debugMSG, "not finished");
            // increase shots
            ++ currentShots;
            // roll every dice which is under the cup
            for(int i=0; i<dicesIn.size();++i){
                dicesIn.get(i).roll();
                Log.d(debugMSG, "Dice["+i+"] = "+ dicesIn.get(i).getValue());
            }
            playerView.uncoverDice();
            // if the current max shots is reached
            if(currentShots == 3) {
                // the player is finished
                finish = true;
                Log.d(debugMSG, "finished");
            }
            // create posibilities
            createPossibilities();
                // return true, that the player could roll his dices
                return true;
        }else{
                // return false, that the player could not roll his dices
                return  false;
        }
    }

    @Override
    public boolean takeDiceInAgain(final Dice dice) throws DiceAlreadyInException {
        // take in the dice
        if(dicesIn.contains(dice)){
            throw new DiceAlreadyInException(dice);
        }
        // add the dice to the list of dices which are under the cup
        dicesIn.add(dice);
        // remove the dice of the list of dices which are out
        return dicesOut.remove(dice);
    }

    @Override
    public boolean takeDiceOut(final Dice dice) throws DiceAlreadyOutException {
        if(dicesOut.contains(dice)){
            throw new DiceAlreadyOutException(dice);
        }
        // add the dice to the list of dices which are out
        dicesOut.add(dice);
        // remove the dice of the list of dices which are under the cup.
        return dicesIn.remove(dice);
    }

    @Override
    public void turnAround() throws Exception {

    }

    /**
     *
     */
    private void createPossibilities(){
        /*
        - It is his turn
          - roll the dice

        - 1. time rolled
           - blind
           - uncover
             - stay
             - roll the dice
             - 2 times six -> 1 time one
             - 3 times six -> 2 time one
         - 2. time rolled
           - uncover (from now on automatically? )
             - stay
             - roll the the dice
             - 2 times six -> 1 time one
             - 3 times six -> 2 time one
           - stay blind ?
         - 3. time rolled
             - do not uncover -> next player
         */

        if(currentShots == 0 && dicesIn.size() == 3){
            Log.d(debugMSG,"currentShots == 0 && dicesIn.size() == 3");
            playerView.enableRollTheDiceButton();
            // first time roll

        }else{
            if(currentShots == 1 && isStartPlayer){
                Log.d(debugMSG,"currentShots == 1 && isStartPlayer");
                // set visible blind button
                // set visible uncover button
                playerView.enableRollTheDiceButton();
                playerView.enableStayButton();
            }else{
                // is the player able to roll
                if(currentShots < gameObserver.getMaxShotsOfRound()){
                    Log.d(debugMSG,"currentShots < gameObserver.getMaxShotsOfRound()");
                    // set visible uncover button
                }else{
                    Log.d(debugMSG,"else");
                    // not uncover
                    gameObserver.currentPlayerHasFinished();
                }
            }

        }

    }


}
