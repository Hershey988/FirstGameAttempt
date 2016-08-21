package com.example.alex.animationtween;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by Alex on 8/20/2016.
 */
public class DrawingBall extends View {
    Bitmap bball;
    int positionX, positionY;


    public DrawingBall(Context context) {
        super(context);
        bball = BitmapFactory.decodeResource(getResources(), R.drawable.blueball);
        positionX = 0;
        positionY = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Rect ourRect = new Rect();
        ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);
        canvas.drawRect(ourRect, blue);
        if ( positionX < canvas.getWidth()) {
            positionX += 5;
        } else {
            positionX = 0;
        }
        if (positionY < canvas.getWidth()) {
            positionY += 5;
        } else {
            positionY = 0;
        }
        Paint p = new Paint();
        canvas.drawBitmap(bball, positionX, positionY, p);
        invalidate();
    }
}
