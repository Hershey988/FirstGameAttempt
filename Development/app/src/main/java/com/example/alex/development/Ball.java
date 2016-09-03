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
    private Bitmap ballImg;
    int speedX;
    int speedY;

    public Ball(Bitmap image) {
        ballInit();
        setImage(image);
        Random r = new Random();
        speedX = r.nextInt(10);
        speedY = r.nextInt(10);

        if(speedX == 0 && speedY == 0){
            speedX = 1;
            speedY = 1;
        }
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

    public void move(int width, int height){
        int radius = ballImg.getWidth() / 2;
        //in X direction
        if((posX+radius) >= width-90){
            speedX = -1 * speedX;
        }
        else if((posX-radius) <= -90){
            speedX = Math.abs(speedX);
        }

        //In Y direction
        if((posY+radius) >= height-90){
            speedY = -1 * speedY;
        }
        else if((posY-radius) <= -90){
            speedY = Math.abs(speedY);
        }
    }
}
