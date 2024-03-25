package com.sphy.game.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Character{

    public Bullet(Texture texture, Vector2 position) {
        super(texture, position);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}