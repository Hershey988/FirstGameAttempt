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
    public void menuPress(View view) {
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
    }

    public void playPress(View view) {
        Intent intent = new Intent(getApplicationContext(), Play.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        TextView scoreBoard = (TextView) findViewById(R.id.ScoreText);

        Bundle getResults = getIntent().getExtras(); //Gets results from the last activity Play
        String score = "";
        if(getResults != null) {
            score = getResults.getString("Score");
        }
        scoreBoard.setText("Congradulations your score was:" + score);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
