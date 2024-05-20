package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sphy.game.manager.ResourceManager;

public class PowerUp implements Disposable {
    public Texture texture;
    public Vector2 position;

    public Rectangle rect;

    public PowerUp(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;

        rect = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void draw(Batch batch) {

        batch.draw(texture, position.x, position.y);
    }

    public void move(float x, float y) {
        position.add(x, y);
        rect.x += x;
        rect.y += y;
    }

    @Override
    public void dispose() {
    }
}
