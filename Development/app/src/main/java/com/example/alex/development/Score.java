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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        String scoreKey = getResources().getString(R.string.score_key);

//        String scoreKey = getString(R.string.score_key);
        SharedPreferences getScore = getSharedPreferences(scoreKey, Context.MODE_PRIVATE);

        String[] topScores = getResources().getStringArray(R.array.top_scores);

        TextView scoreBoard = (TextView) findViewById(R.id.score1);
        int textViewId;
        int temp;           //temp will hold the high score values
        String[] id = new String[]{"score1","score2","score3","score4","score5"};
        for (int i = 0; i < topScores.length; i++) {
            temp = getScore.getInt(topScores[i], 0);
            textViewId = getResources().getIdentifier(id[i], "id", getPackageName());
            scoreBoard = (TextView) findViewById(textViewId);
            int scoreNumber = i + 1;
            //example of setTExt layout "1. Score: 9999"
            scoreBoard.setText( scoreNumber + ". Score: " + temp);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
