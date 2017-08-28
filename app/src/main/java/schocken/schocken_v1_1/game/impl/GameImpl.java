package schocken.schocken_v1_1.game.impl;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import schocken.schocken_v1_1.game.Game;
import schocken.schocken_v1_1.gameobserver.GameObserver;
import schocken.schocken_v1_1.player.Player;
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


    public GameImpl(final GameView gameView,final PlayerView playerView, @NonNull final String[] players){
        this.gameView = gameView;
        this.players = createPlayers(playerView, players);
        gameView.addPlayers(this.players);
    }


    @Override
    public void startNewgame() {

    }

    @Override
    public int getMaxShotsOfRound() {
        return 0;
    }

    @Override
    public void currentPlayerHasFinished() {

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
}
