package com.sphy.game.items;

public class Stone {

    private float x;
    private float y;
    private float width;
    private float heigth;


    public Stone(float x, float y, float width, float heigth) {
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
