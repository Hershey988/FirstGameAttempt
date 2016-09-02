package com.example.alex.development;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Alex on 9/2/2016.
 */
public class PlaySurface extends Activity {
    DrawBallSurface ourBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourBall = new DrawBallSurface(this);
        setContentView(ourBall);
    }
}
