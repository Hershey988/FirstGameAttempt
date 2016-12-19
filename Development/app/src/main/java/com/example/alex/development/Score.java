package com.example.alex.development;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

//    public void playPress(View view) {
//        //Keep the score that is currently being display
//        Intent intent = new Intent(getApplicationContext(), LoadingScreen.class);
//        intent.putExtra("Score", score);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);

        String scoreKey = getString(R.string.score_key);
        SharedPreferences getScore = getSharedPreferences(scoreKey, Context.MODE_PRIVATE);

        String[] topScores = getResources().getStringArray(R.array.top_scores);

        int[] highscores = new int[5];

        for (int i = 0; i < topScores.length; i++) {
            highscores[i] = getScore.getInt(topScores[i], 0);
        }

        TextView scoreBoard = (TextView) findViewById(R.id.score1);
        scoreBoard.setText("1. Score: " + highscores[0]);
        scoreBoard = (TextView) findViewById(R.id.score2);
        scoreBoard.setText("2. Score: " + highscores[1]);
        scoreBoard = (TextView) findViewById(R.id.score3);
        scoreBoard.setText("3. Score: " + highscores[2]);
        scoreBoard = (TextView) findViewById(R.id.score4);
        scoreBoard.setText("4. Score: " + highscores[3]);
        scoreBoard = (TextView) findViewById(R.id.score5);
        scoreBoard.setText("5. Score: " + highscores[4]);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
