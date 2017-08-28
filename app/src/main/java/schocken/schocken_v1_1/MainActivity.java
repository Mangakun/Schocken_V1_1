package schocken.schocken_v1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import schocken.schocken_v1_1.view.gameview.GameView;

public class MainActivity extends AppCompatActivity {

    /**
     * A final debug string
     */
    private final String debugString = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   public void startTheGame(final View view) {
        Log.d(debugString, "startGame");
        final Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("players", new String[]{"Marco","Michelle"});
        startActivity(intent);
    }
}
