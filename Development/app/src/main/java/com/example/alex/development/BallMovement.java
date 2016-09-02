package com.example.alex.development;

/**
 * Created by Harsh on 9/2/2016.
 */
public class BallMovement {
    private int posX;
    private int posY;
    private int height;
    private int width;
    private int radius;


    public BallMovement(int posX, int posY, int height, int width, int radius){
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
        this.radius = radius;
    }

    public int AtWall(){
        if((posX + radius) == width || (posY+radius) == height || (posX - radius) == 0 || (posY-radius) == 0){
            return 1;
        }
        return 0;
    }

    public void MoveBall(){

    }
}
