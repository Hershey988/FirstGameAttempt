package com.example.alex.development;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

/**
 * Created by Alex on 9/12/2016.
 */


public class LoadingScreen extends AppCompatActivity{
    Random r = new Random();
    final int game_ball_color  = r.nextInt(3) + 1;
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

        CounterClass countDown = new CounterClass(3100, 1000);
        countDown.start();

    }

    public void setBall(int color){
        if (color == RED) {
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.redball);
        } else if (color == GREEN) {
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
        } else if (color == BLUE) {
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
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
            myTimer= String.format(Locale.US, "%02d",  seconds);
            TextView loadScreen = (TextView) findViewById(R.id.loadingTime);
            loadScreen.setText(myTimer);
        }


        //Executed OnFinish once only when timer reaches 0
        public void onFinish() {
            Intent play = new Intent(getApplicationContext(), Play.class);
            play.putExtra("game_ball_color", game_ball_color);
            startActivity(play);
        }
    }
}
