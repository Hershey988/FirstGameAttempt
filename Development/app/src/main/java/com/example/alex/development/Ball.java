package com.example.alex.development;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by Harsh on 9/2/2016.
 */
public class Ball extends View{

    private int posX;
    private int posY;
    private Bitmap gBall;



    public Ball(Context context) {
        super(context);

    }

    public void ballInit(){
        Random r = new Random();
        int range = 500;
        posX = r.nextInt(range);
        posY = r.nextInt(range);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap gBall2 = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
        gBall = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
        canvas.drawBitmap(gBall, getPositionY(), getPositionX(), null);
        canvas.drawBitmap(gBall2, 150, 150, null);
        invalidate();


    }

    public void setPositionX(int posX) {
        this.posX += posX;
    }

    public void setPositionY(int posY) {
        this.posY += posY;
    }

    public int getPositionX() {
        return posX;
    }

    public int getPositionY() {
        return posY;
    }


}
