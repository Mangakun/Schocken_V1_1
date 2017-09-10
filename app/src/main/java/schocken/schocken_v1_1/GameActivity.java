package schocken.schocken_v1_1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import schocken.schocken_v1_1.adapter.PlayerAdapter;
import schocken.schocken_v1_1.game.Game;
import schocken.schocken_v1_1.game.impl.GameImpl;
import schocken.schocken_v1_1.listener.DiceClickListener;
import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.player.dice.Dice;
import schocken.schocken_v1_1.view.gameview.GameView;
import schocken.schocken_v1_1.view.playerview.PlayerView;

/**
 * Created by marco on 25.08.17.
 */

public class GameActivity extends AppCompatActivity implements GameView,PlayerView{


    private PlayerAdapter playerAdapter;


    private ImageButton[] diceButtons;


    private final String debugString = "GameActivity";

    private TextView playerName;

    private TextView playerShots;

    private TextView playerPenalties;
    /**
     *
     * More like a GamePresenter
     */
    private Game game;

    /**
     *
     * More like a PlayerPresenter
     */
    private Player currentPlayer;

    private Drawable[] dicePictures;

    private Button stayButton;

    private Button rollTheDiceButton;

    private Button upButton;

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
        // init buttons
        initButtons();
        // init dice buttons
        initDiceButtons();
        // init dice pictures
        initDicePictures();
        // create a new game
        game = new GameImpl(this,this,players);
        // start a new game
        game.startNewgame();
    }


    private void initListViewAndAdapter(){
        //ListView listView = (ListView) findViewById(R.id.PlayerView);
        //playerAdapter = new PlayerAdapter(this,R.layout.player_row);
        //listView.setAdapter(playerAdapter);
        playerName = (TextView) findViewById(R.id.playerName);
        playerShots = (TextView) findViewById(R.id.playerShots);
        playerPenalties = (TextView) findViewById(R.id.playerPenalties);
    }

    private void initButtons(){
        rollTheDiceButton = (Button) findViewById(R.id.rollTheDice);
        stayButton = (Button) findViewById(R.id.stay);
        upButton = (Button) findViewById(R.id.up);
    }

    /**
     * This method initializes image buttons
     */
    private void initDiceButtons(){
        diceButtons = new ImageButton[]{(ImageButton) findViewById(R.id.diceButton1), (ImageButton) findViewById(R.id.diceButton2), (ImageButton) findViewById(R.id.diceButton3)};
    }


    private void initDicePictures(){
        dicePictures = new Drawable[]{
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice1,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice2,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice3,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice4,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice5,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice6,getTheme()),
        };
    }


    @Override
    public void clearPlayerView() {
        currentPlayer = null;
        disableBlindButton();
        disableStayButton();
        disableRollTheDiceButton();
        disableUncoverButton();
        disableDices();
    }

    private void disableDices(){
        for(int i=0; i<diceButtons.length;++i){
            diceButtons[i].setVisibility(View.GONE);
        }
    }

    @Override
    public void enableRollTheDiceButton() {
        rollTheDiceButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableRollTheDiceButton() {
        rollTheDiceButton.setVisibility(View.GONE);
    }

    @Override
    public void enableBlindButton() {

    }

    @Override
    public void disableBlindButton() {

    }

    @Override
    public void enableUncoverButton() {
        upButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableUncoverButton() {
        upButton.setVisibility(View.GONE);
    }

    @Override
    public void enableStayButton() {
        stayButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableStayButton() {
        stayButton.setVisibility(View.GONE);
    }

    @Override
    public void uncoverDice() {
        // get list of dices which are under the cup
        final List<Dice> dicesUnderCup = currentPlayer.getDicesUnderTheCup();
        // iterate over these dices
        for(int i=0; i< dicesUnderCup.size();++i){
            diceButtons[i].setOnClickListener(new DiceClickListener(currentPlayer,dicesUnderCup.get(i)));
            diceButtons[i].setImageDrawable(dicePictures[dicesUnderCup.get(i).getValue()-1]);
            diceButtons[i].setVisibility(View.VISIBLE);
            diceButtons[i].setBackgroundColor(0);
        }
    }

    @Override
    public void setCurrentPlayer(final Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        updatePlayerInfo();
    }

    @Override
    public void updatePlayerInfo() {

        playerName.setText(currentPlayer.getName());
        playerShots.setText(String.valueOf(currentPlayer.getCurrentShots()));
        playerPenalties.setText(String.valueOf(currentPlayer.getPenalties()));

    }

    @Override
    public void addPlayers(final List<Player> players) {
        //playerAdapter.addAll(players);
    }


    public void rollTheDice(final View view){
        Log.d(debugString, "rollTheDice");
        currentPlayer.rollTheDice();
    }

    public void stay(final View view){
        try {
            currentPlayer.stay();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void up(final View view){
        currentPlayer.up();

    }
}
