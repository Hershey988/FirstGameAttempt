package com.example.alex.development;

import java.util.Random;

/**
 * Created by Alex on 9/2/2016.
 */
public class BallCoordinates {
    int positionX, positionY;

    public BallCoordinates() {
        Random r = new Random();
        int range = 500;
        positionX = r.nextInt(range);
        positionY = r.nextInt(range);
    }

    public void setPositionX(int positionX) {
        this.positionX += positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY += positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

}
