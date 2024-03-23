package com.sphy.game.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Character implements Disposable {
    public Texture texture;
    public Vector2 position;

    public Rectangle rect;

    public Character(Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;

        rect = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void move(float x, float y) {
        position.x += x;
        position.y += y;
        rect.x += x;
        rect.y += y;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
