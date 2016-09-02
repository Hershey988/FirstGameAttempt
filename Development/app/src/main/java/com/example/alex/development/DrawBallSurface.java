package com.example.alex.development;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Alex on 9/2/2016.
 */
public class DrawBallSurface extends SurfaceView implements Runnable{
    SurfaceHolder ourHolder;
    Thread ourThread = null;
    boolean isRunning = true;

    public DrawBallSurface(Context context){
        super(context);
        ourHolder = getHolder();
        ourThread = new Thread(this);
        ourThread.start();
    }

    @Override
    public void run() {
    while(isRunning){
        if(!ourHolder.getSurface().isValid())
            continue;
        Canvas canvas = ourHolder.lockCanvas();
        canvas.drawRGB(02, 02, 02);
        ourHolder.unlockCanvasAndPost(canvas);
        }
    }
}
