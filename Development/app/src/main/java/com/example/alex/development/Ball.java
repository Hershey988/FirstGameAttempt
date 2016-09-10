package com.example.alex.development;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import java.util.Random;

/**
 * Created by Harsh on 9/2/2016.
 */
public class Ball extends AppCompatActivity {

    public final int color;   //Red is 1 Blue is 2 Green is 3
    int speedX;
    int speedY;

    private int posX;
    private int posY;
    private Bitmap ballImg;

    public Ball(Bitmap image, int ballColor) {
        ballInit();
        setImage(image);
        Random r = new Random();
        speedX = r.nextInt(10) + 1; // + 1 prevents the possibility of getting 0 speed
        speedY = r.nextInt(10) + 1; // + 1 prevents the possibility of getting 0 speed
        color = ballColor;
    }


    /*
    * Location spawn of the ball is initialized here
    * In future we should make the direction randomized as well
    *
    * */
    public void ballInit(){
        Random r = new Random();
        int range = 400;
        int title_buffer = 100; //Accounts for the top part of the screen

        posX = r.nextInt(range) + title_buffer;
        posY = r.nextInt(range) + title_buffer;
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

    public int getBallColor(){
        return color;
    }

    //uses the width and height of our screen display
    public void move(int width, int height){
        int radius = ballImg.getWidth() / 2;
        int buffer = 100;

        //in X direction
        if((posX+radius) >= width-buffer){
            speedX = -1 * speedX;
        }
        else if((posX - radius) <= 0-buffer){
            speedX = Math.abs(speedX);
        }

        //In Y direction
        if((posY+radius) >= height - buffer){
            speedY = -1 * speedY;
        }
        else if((posY-radius) <= ((radius * 2) + 10 - buffer)){  //So here I had to also take into account the top part of the display
            speedY = Math.abs(speedY);                           // where we show which ball to get rid off. so i multiply the radius by 2 and add 10pixels more so it looks nice.
        }
    }
}
