package com.example.alex.development;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alex on 9/8/2016.
 */
public class Score extends Activity {
    static int score = -1; //If we return -1 then we didn't load our last score

    public void menuPress(View view) {
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
    }

    public void playPress(View view) {
        //Keep the score that is currently being display
        Intent intent = new Intent(getApplicationContext(), LoadingScreen.class);
        intent.putExtra("Score", score);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        TextView scoreBoard = (TextView) findViewById(R.id.ScoreText);

        Bundle getResults = getIntent().getExtras(); //Gets results from the last activity Play
        if(getResults != null) {
            score = getResults.getInt("Score");
        }
        scoreBoard.setText("Congradulations your score was:" + score);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
