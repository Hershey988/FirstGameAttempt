package com.example.alex.animationtween;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Alex on 8/21/2016.
 */
public class drawingThread extends Activity implements View.OnTouchListener {
    OurView v;
    Bitmap bball;

    float positionX, positionY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = new OurView(this);
        v.setOnTouchListener(this);
        setContentView(v);

        bball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
        positionX = 0;
        positionY = 0;


    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }


    public class OurView extends SurfaceView implements Runnable {
        Thread Balls = null;
        SurfaceHolder holder;
        boolean updateBall = false;

        protected OurView(Context context) {
            super(context);
            holder = getHolder();
        }

        public void run() {
            while(updateBall == true){
                if(!holder.getSurface().isValid()){
                    continue;
                }
                Canvas c = holder.lockCanvas();
                // First integer is transpariancy (255 is highest) the following
                //three integers are RGB values
                c.drawARGB(155, 150, 150, 150);
                int offsetX = bball.getWidth()/2;
                int offsetY = bball.getHeight()/2;

                if ( positionX < c.getWidth()) {
                    positionX += 5;
                } else {
                    if( positionX > -1) {
                        positionX = 0;
                    }

                }
                if (positionY < c.getWidth()) {
                    positionY += 5;
                } else {
                    positionY = 0;
                }
                c.drawBitmap(bball, positionX - offsetX, positionY - offsetY, null);
                holder.unlockCanvasAndPost(c);

            }
        }

        public void pause() {
            updateBall = false;
            while(true){
                try{
                    Balls.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;

            }
            Balls = null;
        }

        public void resume() {
            updateBall = true;
            Balls = new Thread(this);
            Balls.start();

        }


    }

    public boolean onTouch(View v, MotionEvent me) {

        if((positionX + 25) > me.getX() && (positionX - 25) < me.getX() )
        {
            positionX = -999999;       //Erase from the game
        }
        if((positionY + 25) > me.getY() && (positionY - 25) < me.getY() )
        {
            positionY = -999999;       //Erase from the game
        }
        //positionX = me.getX();          //Position in which the user touch
        //positionY = me.getY();

        switch(me.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
        }

        return false;
    }

}
