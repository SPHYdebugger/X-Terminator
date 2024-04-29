package com.sphy.game.items;

public class Goal {
    private float x;
    private float y;
    private float width;
    private float heigth;


    public Goal(float x, float y, float width, float heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeigth() {
        return heigth;
    }
}
