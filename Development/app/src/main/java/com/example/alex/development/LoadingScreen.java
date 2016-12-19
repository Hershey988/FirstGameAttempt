package com.example.alex.development;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

/**
 * Created by Alex on 9/12/2016.
 */


public class LoadingScreen extends AppCompatActivity {
    Random r = new Random();
    final int game_ball_color = r.nextInt(3) + 1;
    final int RED = 1;
    final int GREEN = 2;
    final int BLUE = 3;
    Bitmap ball;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);
        ImageView gameBall = (ImageView) findViewById(R.id.BallKeeper);
        setBall(game_ball_color);
        gameBall.setImageBitmap(ball);
        //CounterClass(Counter in Miliseconds, tick)
        CounterClass countDown = new CounterClass(1, 1);
        TextView loadScreen = (TextView) findViewById(R.id.ScoreKeeper);
        Bundle getInfo = getIntent().getExtras();

        try {
            loadScreen.setText("Score: " + getInfo.getInt("Score"));
        } catch (NullPointerException e) {
            loadScreen.setText("Score: 0000");
        }

        //If there is no level defined, assume we are at level 1



        countDown.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView loadScreen = (TextView) findViewById(R.id.ScoreKeeper);
        Bundle getScore = getIntent().getExtras();
        try {
            loadScreen.setText("Score: " + getScore.getInt("Score"));
        } catch (NullPointerException e) {
            loadScreen.setText("Score: 0000");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void setBall(int color) {
        switch (color) {
            case RED:
                ball = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
                break;
            case GREEN:
                ball = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
                break;
            case BLUE:
                ball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
                break;
            default:
                System.out.println("You somehow broke the color setball");
                break;
        }
    }

    String myTimer;

    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisOfTime, long countDownInterval) {
            super(millisOfTime, countDownInterval);
        }

        //Executes Tick every countdownInterval in this case interval is 1 second
        public void onTick(long millisRemaining) {
            final int secondConv = 1000;
            final int converter = 60;
            long Tempseconds = millisRemaining / secondConv;
            long minutes = Tempseconds / converter;
            long seconds = (Tempseconds % converter);
            myTimer = String.format(Locale.US, "%02d", seconds);
            TextView loadScreen = (TextView) findViewById(R.id.loadingTime);
            loadScreen.setText(myTimer);
        }


        //Executed OnFinish once only when timer reaches 0
        public void onFinish() {
            Bundle getInfo = getIntent().getExtras();
            Intent play = new Intent(getApplicationContext(), Play.class);
            int level;
            try {
                level = getInfo.getInt("Level");
            } catch (NullPointerException e) {
                level = 0;
            }
             /*
            This should be were we set up the level difficulty
            Extras should include the following
            Score from previous level
            Number of balls being place
            Speed of the balls
            Amount Of time
            Bonus Effects will be lights out.
            */
            if (getInfo != null) {
                play.putExtra("Score", getInfo.getInt("Score"));
                play.putExtra("Level", ++level);
            }
            play.putExtra("game_ball_color", game_ball_color);
            startActivity(play);
        }
    }
}
