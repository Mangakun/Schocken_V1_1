package schocken.schocken_v1_1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import schocken.schocken_v1_1.game.Game;
import schocken.schocken_v1_1.game.impl.GameImpl;
import schocken.schocken_v1_1.player.Player;
import schocken.schocken_v1_1.view.gameview.GameView;
import schocken.schocken_v1_1.view.playerview.PlayerView;

/**
 * Created by marco on 25.08.17.
 */

public class GameActivity extends AppCompatActivity implements GameView,PlayerView{

    /**
     * TODO: documentation.
     */
   private ImageButton[] diceButtons;

    /**
     * TODO: documentation.
     */
    private final String debugString = "GameActivity";
    /**
     * TODO: documentation.
     */
    private TextView playerName;
    /**
     * TODO: documentation.
     */
    private TextView playerShots;
    /**
     * TODO: documentation.
     */
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
    /**
     * TODO: documentation.
     */
    private Drawable[] dicePictures;
    /**
     * TODO: documentation.
     */
    private Button stayButton;
    /**
     * TODO: documentation.
     */
    private Button rollTheDiceButton;
    /**
     * TODO: documentation.
     */
    private Button uncoverButton;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(debugString,"on create");
        super.onCreate(savedInstanceState);
        // set content view
        setContentView(R.layout.activity_game);
        // get intent
        final Intent intent = getIntent();
        // get player names
        final String[] players = intent.getStringArrayExtra("players");
        // init the list view and the adapter
        initTextViews();
        // init buttons
        initButtons();
        // init dice buttons
        initDiceButtons();
        // init dice pictures
        initDiceButtonPictures();
        // create a new game
        game = new GameImpl(this,this,players);
        // start a new game
        game.startNewgame();
    }

    /**
     * This method initializes the text views.
     */
    private void initTextViews(){
        Log.d(debugString,"init text views");
        playerName = (TextView) findViewById(R.id.playerName);
        playerShots = (TextView) findViewById(R.id.playerShots);
        playerPenalties = (TextView) findViewById(R.id.playerPenalties);
    }

    /**
     * This method initializes the buttons.
     */
    private void initButtons(){
        Log.d(debugString,"init buttons");
        rollTheDiceButton = (Button) findViewById(R.id.rollTheDice);
        stayButton = (Button) findViewById(R.id.stay);
        uncoverButton = (Button) findViewById(R.id.up);
    }

    /**
     * This method initializes image buttons
     */
    private void initDiceButtons(){
        Log.d(debugString,"init dice buttons");
        diceButtons = new ImageButton[]{(ImageButton) findViewById(R.id.diceButton1), (ImageButton) findViewById(R.id.diceButton2), (ImageButton) findViewById(R.id.diceButton3)};
    }

    /**
     * This method initializes the dice pictures.
     */
    private void initDiceButtonPictures(){
        Log.d(debugString,"init dice buttons pictures");
        dicePictures = new Drawable[]{
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice1,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice2,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice3,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice4,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice5,getTheme()),
                ResourcesCompat.getDrawable(getResources(),R.drawable.dice6,getTheme()),
        };
    }


    /**
     * This method disables the dice button.
     */
   private void disableDiceButtons(){
       Log.d(debugString,"disable the dice buttons");
        for(final ImageButton diceButton: diceButtons){
            diceButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void enableRollTheDiceButton() {
        Log.d(debugString,"enable roll the dice button");
        rollTheDiceButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableRollTheDiceButton() {
        Log.d(debugString,"disable roll the dice button");
        rollTheDiceButton.setVisibility(View.GONE);
    }

    @Override
    public void enableBlindButton() {
        Log.d(debugString,"enable blind button");
    }

    @Override
    public void disableBlindButton() {
        Log.d(debugString,"disable blind button");
    }

    @Override
    public void enableUncoverButton() {
        Log.d(debugString,"enable uncover button");
        uncoverButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableUncoverButton() {
        Log.d(debugString,"enable uncover button");
        uncoverButton.setVisibility(View.GONE);
    }

    @Override
    public void enableStayButton() {
        Log.d(debugString,"enable stay button");
        stayButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableStayButton() {
        Log.d(debugString,"disable stay button");
        stayButton.setVisibility(View.GONE);
    }

//    @Override
//    public void uncoverDice() {
//        // get list of dices which are under the cup
//        final List<Dice> dicesUnderCup = currentPlayer.getDicesUnderTheCup();
//        // iterate over these dices
//        for(int i=0; i< dicesUnderCup.size();++i){
//            diceButtons[i].setOnClickListener(new DiceClickListener(currentPlayer,dicesUnderCup.get(i)));
//            diceButtons[i].setImageDrawable(dicePictures[dicesUnderCup.get(i).getValue()-1]);
//            diceButtons[i].setVisibility(View.VISIBLE);
//            diceButtons[i].setBackgroundColor(0);
//        }
//    }

    @Override
    public void setCurrentPlayer(final Player currentPlayer) {
        Log.d(debugString,"set "+currentPlayer.getName()+" into view");
        clearPlayerView();
        this.currentPlayer = currentPlayer;
        updatePlayerInfo();
    }
    /**
     * This method clears the player view.
     */
    private void clearPlayerView() {
        Log.d(debugString,"clear "+currentPlayer.getName()+" from view");
        currentPlayer = null;
        disableBlindButton();
        disableStayButton();
        disableRollTheDiceButton();
        disableUncoverButton();
        disableDiceButtons();
    }


    @Override
    public void updatePlayerInfo() {
        playerName.setText(currentPlayer.getName());
        playerShots.setText(String.valueOf(currentPlayer.getCurrentShots()));
        playerPenalties.setText(String.valueOf(currentPlayer.getPenalties()));

    }
/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++Activity methods++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

    /**
     * This method is executed when the user clicks on the button "Roll the dice".
     * @param view An object of the class {@link View}.
     */
    public void rollTheDice(final View view){
        Log.d(debugString, "rollTheDice button clicked");
        currentPlayer.rollTheDice();
    }

    /**
     * This method is executed when the user clicks on the button "Stay".
     * @param view An pbject of the class {@link View}.
     */
    public void stay(final View view){
        Log.d(debugString,"stay button clicked");
        try {
            currentPlayer.stay();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is executed when the user clicks on the button "Up".
     * @param view An object of the class {@link View}.
     */
    public void up(final View view){
        Log.d(debugString,"Up button clicked");
        currentPlayer.up();

    }
}
