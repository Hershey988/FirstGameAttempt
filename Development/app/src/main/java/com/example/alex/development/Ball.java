package com.example.alex.development;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

/**
 * Created by Harsh on 9/2/2016.
 */
public class Ball extends AppCompatActivity {

    private int posX;
    private int posY;
    private Bitmap gBall;
    private Bitmap ballImg;



    public Ball(Bitmap image) {
        ballInit();
        setImage(image);
    }

    public void ballInit(){
        Random r = new Random();
        int range = 500;
        posX = r.nextInt(range);
        posY = r.nextInt(range);
    }

    public void setImage(Bitmap image) {
        ballImg = image;
    }

    public Bitmap getImage(){
        return ballImg;
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
