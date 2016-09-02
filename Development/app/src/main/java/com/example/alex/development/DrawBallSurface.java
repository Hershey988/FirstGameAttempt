package com.example.alex.development;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Alex on 9/2/2016.
 */
public class DrawBallSurface extends SurfaceView implements Runnable{
    SurfaceHolder ourHolder;
    Thread ourThread = null;
    boolean isRunning = false;

    public DrawBallSurface(Context context){
        super(context);
        ourHolder = getHolder();
        ourThread = new Thread(this);
        ourThread.start();
    }

    public void pause() {
        isRunning = false;
        while(true){
            try {
            ourThread.join();
             } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        ourThread = null;
    }

    public void resume() {
        isRunning = true;
        ourThread = new Thread(this);
        ourThread.start();
    }


    @Override
    public void run() {
    while(isRunning){
        if(!ourHolder.getSurface().isValid())
            continue;
        Canvas canvas = ourHolder.lockCanvas();

        Bitmap gBall;
        gBall = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
        canvas.drawBitmap(gBall, 100, 100, null);
        ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
