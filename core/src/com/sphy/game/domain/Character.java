package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sphy.game.manager.ResourceManager;

public class Character implements Disposable {
    public Texture texture;
    public Vector2 position;

    private Animation<TextureRegion> animation;
    private float stateTime;
    private TextureRegion currentFrame;
    public Rectangle rect;

    public Character(Vector2 position, String animationName) {
        this.position = position;

        animation = new Animation<>(0.15f, ResourceManager.getAnimation(animationName));
        currentFrame = animation.getKeyFrame(0);

        rect = new Rectangle(position.x + 10, position.y, currentFrame.getRegionWidth()-40, currentFrame.getRegionHeight());
    }

    public void draw(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
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
