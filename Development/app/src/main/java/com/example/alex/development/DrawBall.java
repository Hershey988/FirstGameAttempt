package com.example.alex.development;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by Alex on 9/2/2016.
 */
public class DrawBall extends View {

    Bitmap gBall;
    BallCoordinates ballxy;
    int speed = 5;      //moving rate of ball

    enum State {
        upLeft,
        upRight,
        botLeft,
        botRight

    }

    private State _state;
    public DrawBall(Context context) {
        super(context);
        gBall = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
        ballxy = new BallCoordinates();
        _state = State.botRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(gBall, ballxy.getPositionY(), ballxy.getPositionX(), null);


        ballxy.setPositionX(speed);
        ballxy.setPositionY(speed);
        invalidate();
    }

    /*
    public void checkBounds(BallCoordinates ball, Canvas canvas){
        //top of canvas and left of canvas
        int top = 0;
        int left = 0;


        if(((ball.getPositionX() + speed) >  canvas.getWidth()) &&
                (_state == State.botRight))
            _state =  State.botLeft;
        else if(((ball.getPositionX() + speed) >  canvas.getWidth()) &&
        (_state == State.upRight)) {
            _state = State.upLeft;
        }
    }
    */

}
