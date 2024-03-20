package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {
    public Texture texture;
    public Vector2 position;


    public Player(Texture texture) {
        this.texture = texture;
        position = new Vector2(0, 0);
    }

    public Player(Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
    }

    public void manageInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += 20;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= 20;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += 20;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= 20;
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
