package com.example.alex.development;


import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
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

    //uses the width and height of our screen display
    public void move(int width, int height){
        int radius = ballImg.getWidth() / 2;
        int buffer = 90;

        //in X direction
        if((posX+radius) >= width-buffer){
            speedX = -1 * speedX;
        }
        else if((posX-radius) <= -buffer){
            speedX = Math.abs(speedX);
        }

        //In Y direction
        if((posY+radius) >= height - buffer){
            speedY = -1 * speedY;
        }
        else if((posY-radius) <= - buffer){
            speedY = Math.abs(speedY);
        }
    }
}
