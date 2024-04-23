package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sphy.game.manager.ResourceManager;
import lombok.Data;


public class Player extends Character {


    private String playerName;
    private int score;
    public int lives;
    private Animation<TextureRegion> rightAnimation, leftAnimation, stopAnimation;

    private float stateTime;
    private TextureRegion currentFrame;




    public Player(String animationName) {
        super(new Vector2(0, 0), animationName);
        lives = 3;
    }

    public Player(Vector2 position, String animationName) {
        super(position, animationName);
        stopAnimation = new Animation<>(0.15f, ResourceManager.getAnimation("sofiSoldado"));
        rightAnimation = new Animation<>(0.15f, ResourceManager.getAnimation("SofiDER"));
        leftAnimation = new Animation<>(0.15f, ResourceManager.getAnimation("SofiIZQ"));
        lives = 3;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public static void setAnimation(String animationName){

    }
}
