package com.example.alex.development;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

/**
 * Created by Harsh on 9/2/2016.
 */
public class Ball extends AppCompatActivity {

    public final int color;   //Red is 1 Blue is 2 Green is 3
    int speedX = 0;
    int speedY = 0;

    private int posX;
    private int posY;
    private Bitmap ballImg;

    public Ball(Bitmap image, int ballColor, int min, int max) {
        ballInit();
        setImage(image);
        setSpeed(min, max);
        color = ballColor;
    }


    /*
    * Location spawn of the ball is initialized here
    * In future we should make the direction randomized as well
    *
    * */
    public void ballInit() {
        Random r = new Random();
        int range = 400;
        int title_buffer = 100; //Accounts for the top part of the screen


        posX = r.nextInt(range) + title_buffer;
        posY = r.nextInt(range) + title_buffer;
    }

    public void setImage(Bitmap image) {
        ballImg = image;
    }

    public Bitmap getImage() {
        return ballImg;
    }


    /*Set the speed of the ball bounded by min and max
    * Ideally the min and max of the speed is dicated by level
    * max min orginally was 15 and 5 respectively
    * */
    public void setSpeed(int min, int max) {
        Random r = new Random();
        speedX = (r.nextInt(max) + min) * (int) Math.pow((-1), r.nextInt(2)); // + min prevents the possibility of getting 0 speed
        speedY = (r.nextInt(max) + min) * (int) Math.pow((-1), r.nextInt(2)); // * (-1) ^ (0,1) gives us a random direction;
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

    public int getBallColor() {
        return color;
    }

    //uses the width and height of our screen display
    public void move(int width, int height) {

        final int balldimensions = 100;

        int radius = balldimensions / 2;
        int buffer = 75;

        //in X direction
        if ((posX + radius) >= width - buffer) {
            speedX = -1 * speedX;
        } else if ((posX - radius) <= 0 - buffer) {
            speedX = Math.abs(speedX);
        }

        //In Y direction
        if ((posY + radius) >= height - buffer) {
            speedY = -1 * speedY;
        } else if ((posY - radius) <= ((radius * 2) + 60 - buffer)) {  //So here I had to also take into account the top part of the display
            speedY = Math.abs(speedY);                           // where we show which ball to get rid off. so i multiply the radius by 2 and add 10pixels more so it looks nice.
        }
    }

    public boolean bounce() {
        return false;

    }
}
