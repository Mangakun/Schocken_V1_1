package schocken.schocken_v1_1.game.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import schocken.schocken_v1_1.game.Game;
import schocken.schocken_v1_1.gameobserver.GameObserver;
import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.exceptions.MaxPenaltyException;
import schocken.schocken_v1_1.player.impl.PlayerImpl;
import schocken.schocken_v1_1.view.gameview.GameView;
import schocken.schocken_v1_1.view.playerview.PlayerView;

/**
 * Created by marco on 28.08.17.
 */

public class GameImpl implements Game, GameObserver{


    private List<Player> players;

    private Player startPlayer;

    private Player currentPlayer;

    private GameView gameView;

    private int maxShotsOfRound = 3; // TODO: aus Settings nehmen

    private Player bestPlayer;

    private Player worstPlayer;

    private int penaltiesStack = 13; // TODO: aus Settings nehmen

    private boolean roundFinished = false; // TODO: einen anderen Namen wählen

    public GameImpl(final GameView gameView,final PlayerView playerView, @NonNull final String[] players){
        this.gameView = gameView;
        this.players = createPlayers(playerView, players);
        gameView.addPlayers(this.players);
        bestPlayer = null;
        worstPlayer = null;
    }


    @Override
    public void startNewgame() {
        startPlayer = players.get(0);
        currentPlayer = startPlayer;
        currentPlayer.myTurn(true);

    }


    private void startNewRound(){

    }

    @Override
    public int getMaxShotsOfRound() {
        return maxShotsOfRound;
    }

    @Override
    public void currentPlayerHasFinished() {
        if(roundFinished){
            determineBestPlayer();
            determineWorstPlayer();
        }
        if(players.indexOf(currentPlayer)== players.size()-1){
            currentPlayer = players.get(0);
        }else{
            currentPlayer = players.get(players.indexOf(currentPlayer)+1);
        }
        /*
        Ich bin nun wieder beim Startplayer angekommen
        -> setzte round finish auf true
        -> nun müssen alle nacheinander aufdecken
         */
        if(currentPlayer == startPlayer){
            /*
            Erster durchlauf und alle haben gewürfelt
            -> somit ist die Runde fertig und alle können ihren letzten Zug machen
             */
            if(!roundFinished){
                roundFinished = true;

            }else{
                /*
                zweiter durchlauf, nun kann ich die Strafsteine verteilen
                und die Runde ist beendet
                -> die Methode distributePenalties sagt, wie es weiter geht
                 */
                try {
                    distributePenalties();
                } catch (MaxPenaltyException e) {
                    e.printStackTrace();
                }
                roundOrGameFinished();
                return;
            }
        }
        currentPlayer.myTurn(false);
    }


    private void roundOrGameFinished(){
        /*
            sollten alle 13 Steine verbraucht sein
             */
        if(worstPlayer.hasFirstHalf() && worstPlayer.hasSecondHalf()){
                /*
                Das Spiel ist zuende
                Neuanfang -> Der verlorere Spieler wird in Historie gespeichert (Runde ?)
                Neue Runde oder schließen anzeigen
                Auf Tournament-Mode eingehen
                 */
        }else{
                /*
                Weiter mit runde
                Irgendwas wie Weiter soll sichtbar sein!
                 */
        }
    }


    @Override
    public void distributeNewMaxShots() {
        // set only new maximum shots, when the current player is equal the start player
        if(currentPlayer.equals(startPlayer)){
            maxShotsOfRound = currentPlayer.getCurrentShots();
        }
    }

    @Override
    public boolean isRoundFinished() {
        return roundFinished;
    }


    /**
     * This method creates the players for the game.
     * @param playerView
     * @param players
     * @return
     */
    private List<Player> createPlayers(final PlayerView playerView, final String[] players){
        final List<Player> playerList = new ArrayList<>();
        for(final String player: players){
            final Player playerObject = new PlayerImpl(player,this,playerView);
            playerList.add(playerObject);
        }
        return playerList;
    }


    /**
     * TODO: documentation.
     */
    private void determineBestPlayer(){
       if(bestPlayer == null){
           bestPlayer = currentPlayer;
       }else{
           final int bestPlayerDiceValue = bestPlayer.getDiceValue();
           final int currentPlayerDiceValue = currentPlayer.getDiceValue();
           // greater than new player
            if(currentPlayerDiceValue > bestPlayerDiceValue){
                bestPlayer = currentPlayer;
            }else{
                // equal
                if(currentPlayerDiceValue == bestPlayerDiceValue){
                    // look for the shots
                    if (currentPlayer.getCurrentShots() < bestPlayer.getCurrentShots()){
                        bestPlayer = currentPlayer;
                    }
                }
            }
       }
    }

    /**
     * TODO: documentation.
     */
    private void determineWorstPlayer(){
        if(worstPlayer == null){
            worstPlayer = currentPlayer;
        }else{
            final int worstPlayerDiceValue = worstPlayer.getDiceValue();
            final int currentPlayerDiceValue = currentPlayer.getDiceValue();
            // greater than new player
            if(currentPlayerDiceValue < worstPlayerDiceValue){
                bestPlayer = currentPlayer;
            }else{
                // equal
                if(currentPlayerDiceValue == worstPlayerDiceValue){
                    // look for the shots
                    if (currentPlayer.getCurrentShots() > bestPlayer.getCurrentShots()){
                        bestPlayer = currentPlayer;
                    }
                }
            }
        }
    }

    /**
     * TODO: documentation.
     */
    private void distributePenalties() throws MaxPenaltyException {
        if(bestPlayer == worstPlayer){
            throw new RuntimeException("Best player cant be equal worst player");
        }else{
            int penaltiesToDistribute = bestPlayer.calculatePenaltiesOfDiceValue();
            if(penaltiesToDistribute == 13){ // TODO: change to enmum number
                throw new UnsupportedOperationException("Shock out is not implemented yet");
                /*
                worst player bekommt alle penalties -> setPenalties
                alle Anderen bekommen 0 penalties
                er bekommt auch eine weitere hälfte
                - hier muss ich mir noch Gedanken machen, wie ich die beiden Hälften unterscheiden kann -> int oder boolean.
                Update view
                 */

            }else{
                // check if i can take penalties from the stack
                if(penaltiesStack > 0){
                    /*
                    Look up, if the penaltiesStack has the amount of penalties i like to have
                     */
                    penaltiesToDistribute = Math.min(penaltiesStack,penaltiesToDistribute);
                    // take the penalties from the penalties stack
                    penaltiesStack -= penaltiesToDistribute;

                }else{
                    /*
                    Look up, if the winner has the amount of penalties i like to have
                     */
                    penaltiesToDistribute = Math.min(bestPlayer.getPenalties(),penaltiesToDistribute);
                    // take the penalties from the winner
                    try {
                        bestPlayer.addPenalties(-penaltiesToDistribute);
                    } catch (MaxPenaltyException e) {
                        e.printStackTrace();
                    }
                }
                // add penalties to the loser
                try {
                    worstPlayer.addPenalties(penaltiesToDistribute);
                } catch (MaxPenaltyException e) {
                    e.printStackTrace();
                }
            }
            // new round start
            startPlayer = worstPlayer;
        }
    }

    /**
     * TODO: documentation.
     * @param diceValue
     */
    private void getPenalties(int diceValue){

    }
}
