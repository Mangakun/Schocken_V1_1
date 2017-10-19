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
     * A list of dices which set the player out.
     */
    private List<Dice> dicesOut;

    /**
     * A list of dices which has the player under the cup and can roll with it.
     */
    private List<Dice> dicesIn;

    /**
     * The penalties the player has.
     */
    private int penalties;

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
     * An object of the class {@link PlayerView}.
     */
    private PlayerView playerView;


    /**
     * Constructor of the class {@link PlayerImpl}.
     * @param name The name of the player.
     * @param gameObserver An object of the class {@link GameObserver};
     */
    public PlayerImpl(final String name, @NonNull final GameObserver gameObserver, @NonNull final PlayerView playerView){
        Log.d(debugMSG,"constructor of PlayerImpl");
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
        currentShots = 0;
        isStartPlayer = false;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void myTurn(final boolean startPlayer) {
        Log.d(debugMSG,"turn of "+name);
        // save, if this player is the start player of the round
        isStartPlayer = startPlayer;
        // connect the view with this player
        playerView.setCurrentPlayer(this);
        // create now the possibilities he has
        createPossibilities();

    }

    @Override
    public int getDiceValueForCompare() {
        Log.d(debugMSG,"get dice value for compare");
        // create a new arraylist
        List<Dice> dices = new ArrayList<>(dicesOut);
        // sort copy
        Collections.sort(dices);
        // the dices should be sorted here
        for(int i =0; i<dices.size()-1;++i){
            if(dices.get(i).getValue() < dices.get(i).getValue()){
                throw  new RuntimeException("The dice are not sorted!");
            }
        }
        double sum = 0;
        for(int i=dices.size()-1; i >= 0;--i){
            sum += dices.get(i).getValue()* Math.pow(10,i);
        }
        /*
        I need a multiplier, which writes the amount of penalties for the sum.
         */
        int multiplier = 1000; // 1000, because i have three dices.
        /*
        Es kann davon ausgegangen werden, da sie absteigend sortiert sind und
        die zweite Zahl eine 1 ist, dann ist die letzte Zahl auch eine 1 und es handelt sich
        um einen Schock
         */
        if(dices.get(1).getValue() == 1){
            // for shock i want to have a additional zero behind the amount of penalties
            multiplier *= 10;
        }
        return (int)(sum+(getPenaltiesOfDiceValue()* multiplier));
    }


    @Override
    public int getPenaltiesOfDiceValue(){
        Log.d(debugMSG,"getPenaltiesOfDiceValue");
        // create a new arraylist
        List<Dice> dices = new ArrayList<>(dicesOut);
        // sort copy
        Collections.sort(dices);
        // the dices should be sorted here
        for(int i =0; i<dices.size()-1;++i){
            if(dices.get(i).getValue() < dicesOut.get(i).getValue()){
                throw  new RuntimeException("The dice are not sorted!");
            }
        }
        // get dice values
        final int diceValue1 = dices.get(0).getValue();
        final int diceValue2 = dices.get(1).getValue();
        final int diceValue3  = dices.get(2).getValue();
        // check for shock out
        if(diceValue1 == 1 && diceValue2 == 1 && diceValue3==1 ){
            Log.d(debugMSG,"Schock-out return 13");
            return 13;
        }
        if(diceValue2 == 1 && diceValue3 == 1){
            Log.d(debugMSG,"Schock return "+diceValue1);
            return diceValue1;
        }
        // check for general
        if(diceValue1 == diceValue2 && diceValue2 == diceValue3){
            Log.d(debugMSG,"General return 13");
            return 3 ;
        }
        // check for street
        if(diceValue2 == diceValue1-1 && diceValue3 == diceValue2-1){
            Log.d(debugMSG, "street return 2");
            return 2;
        }
        // normal house number
        return 1;
    }

    @Override
    public void addPenalties(int penalties) throws MaxPenaltyException {
        Log.d(debugMSG,name + " gets "+penalties+" penalties");
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
    public int getCurrentShots() {
        return currentShots;
    }

    @Override
    public List<Dice> getDicesOut() {
        return dicesOut;
    }

    @Override
    public List<Dice> getDicesIn() {
        return dicesIn;
    }

    @Override
    public void reset() {
        Log.d(debugMSG,"reset");
        penalties = 0;
        Log.d(debugMSG,"penalties = "+penalties);
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
        gameObserver.distributeNewMaxShots();
        final int dicesInSize = dicesIn.size();
        for(int i=0; i< dicesInSize;++i){
            takeDiceOut(dicesIn.get(0));
        }
        System.out.println("HAEH");
        gameObserver.currentPlayerHasFinished();
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

           if(playerView != null) {
               playerView.uncoverDices();
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
        Log.d(debugMSG,"take dice in again");
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
        Log.d(debugMSG,"take dice out");
        if(dicesOut.contains(dice)){
            throw new DiceAlreadyOutException(dice);
        }
        // add the dice to the list of dices which are out
        dicesOut.add(dice);
        // remove the dice of the list of dices which are under the cup.
        return dicesIn.remove(dice);
    }


    @Override
    public void uncover() {
        Log.d(debugMSG,"uncover");
        if(playerView != null) {
            playerView.uncoverDices();
            playerView.disableUncoverButton();
            playerView.enableStayButton();
        }
    }

    /**
     *
     */
    private void createPossibilities() {
        if (playerView != null && gameObserver != null) {


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

            playerView.updatePlayerInfo();


            // first time -> only roll the dice is visible
            if (currentShots == 0 && dicesIn.size() == 3) {
                Log.d(debugMSG, "currentShots == 0 && dicesIn.size() == 3");
                playerView.enableRollTheDiceButton();
            } else {
            /*
                second time and third time
             */
                if (currentShots < gameObserver.getMaxShotsOfRound()) {
                    Log.d(debugMSG, "currentShots(" + currentShots + ") < maxShotsOfRound (" + gameObserver.getMaxShotsOfRound() + ")");
                    playerView.enableRollTheDiceButton();
                    playerView.enableStayButton();
                } else {
                /*
                Wenn die Runde noch nicht fertig ist, aber ich habe gewürfelt
                 */
                    if (!gameObserver.isRoundFinished()) {
                    /*
                    Ich habe nun fertig gewürfelt
                     */
                        Log.d(debugMSG, name + " hat dreimal gewürfelt!");
                        gameObserver.currentPlayerHasFinished();
                    } else {
                    /*
                    Habe ich schon alle aufgedeckt, dann bin ich halt fertig
                     */
                        if (dicesOut.size() == 3) {
                            gameObserver.currentPlayerHasFinished();
                        } else {
                        /*
                        Er kann nur noch anheben
                         */
                            playerView.disableRollTheDiceButton();
                            playerView.disableStayButton();
                            playerView.enableUncoverButton();
                        }
                    }
                }
            }
        }
    }
}
