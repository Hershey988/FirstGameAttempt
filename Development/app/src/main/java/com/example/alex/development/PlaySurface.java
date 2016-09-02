package com.example.alex.development;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Alex on 9/2/2016.
 */
public class PlaySurface extends Activity implements View.OnTouchListener {
    DrawBallSurface ourBall;
    float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourBall = new DrawBallSurface(this);
        ourBall.setOnTouchListener(this);
        x = 0;
        y = 0;
        setContentView(ourBall);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ourBall.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ourBall.resume();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        x = motionEvent.getX();
        y = motionEvent.getY();

        return false;
    }
}
