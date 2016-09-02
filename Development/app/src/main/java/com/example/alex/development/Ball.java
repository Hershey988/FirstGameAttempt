package com.example.alex.development;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public void BallInit(){
        Random r = new Random();
        int range = 500;
        posX = r.nextInt(range);
        posY = r.nextInt(range);
    }

    public void DrawBall(){
        gBall = BitmapFactory.decodeResource(getResources(), R.drawable.greenball);
        BallInit();
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
