package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import lombok.Data;


public class Player extends Character {

    public int score;
    public int lives;




    public Player(Texture texture) {
        super(texture, new Vector2(0, 0));
        lives = 3;
    }

    public Player(Texture texture, Vector2 position) {
        super(texture, position);
        lives = 3;
    }






    @Override
    public void dispose() {
        texture.dispose();
    }
}
