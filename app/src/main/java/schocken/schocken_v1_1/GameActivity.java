package schocken.schocken_v1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import schocken.schocken_v1_1.adapter.PlayerAdapter;
import schocken.schocken_v1_1.game.Game;
import schocken.schocken_v1_1.game.impl.GameImpl;
import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.view.gameview.GameView;
import schocken.schocken_v1_1.view.playerview.PlayerView;

/**
 * Created by marco on 25.08.17.
 */

public class GameActivity extends AppCompatActivity implements GameView,PlayerView{


    PlayerAdapter playerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.activity_game);
        // get intent
        final Intent intent = getIntent();
        // get player names
        final String[] players = intent.getStringArrayExtra("players");
        // init the list view and the adapter
        initListViewAndAdapter();
        // create a new game
        final Game game = new GameImpl(this,this,players);
        // start a new game
        //gameObserver.startNewgame();
    }


    private void initListViewAndAdapter(){
        ListView listView = (ListView) findViewById(R.id.PlayerView);
        playerAdapter = new PlayerAdapter(this,R.layout.player_row);
        listView.setAdapter(playerAdapter);
    }


    @Override
    public void enableRollTheDiceButton() {

    }

    @Override
    public void disableRollTheDiceButton() {

    }

    @Override
    public void enableBlindButton() {

    }

    @Override
    public void disableBlindButton() {

    }

    @Override
    public void enableUncoverButton() {

    }

    @Override
    public void disableUncoverButton() {

    }

    @Override
    public void enableStayButton() {

    }

    @Override
    public void disableStayButton() {

    }

    @Override
    public void addPlayers(final List<Player> players) {
        playerAdapter.addAll(players);
    }
}
