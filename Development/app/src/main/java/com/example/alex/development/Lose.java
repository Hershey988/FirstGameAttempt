package com.example.alex.development;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Alex on 9/9/2016.
 */
public class Lose extends Activity {

    public void menuPress(View view) {
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
    }

    public void playPress(View view) {
        Intent intent = new Intent(getApplicationContext(), LoadingScreen.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose_screen);
        TextView loseScreen = (TextView) findViewById(R.id.loseText);

        Bundle getResults = getIntent().getExtras(); //Gets results from the last activity Play
        int score = -1;
        if(getResults != null) {
            score = getResults.getInt("Score");
        }
        if(score < 2) {
            score = 0;
        }
        loseScreen.setText("Time's up! Your score was:" + score);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
