package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sphy.game.manager.ResourceManager;
import lombok.Data;


public class Player implements Disposable {
    public Texture texture;
    public Vector2 position;

    private Animation<TextureRegion> animation;
    private float stateTime;
    public TextureRegion currentFrame;
    public Rectangle rect;

    private int score;
    public int lives;

    public Player(Vector2 position, String animationName) {
        this.position = position;

        animation = new Animation<>(0.15f, ResourceManager.getAnimation(animationName));
        currentFrame = animation.getKeyFrame(0);

        rect = new Rectangle(position.x + 10, position.y, currentFrame.getRegionWidth()-40, currentFrame.getRegionHeight());
        lives = 3;
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

    public void changeAnimation(String newAnimationName) {
        if (!newAnimationName.equals(animation)) {

            this.animation = new Animation<>(0.15f, ResourceManager.getAnimation(newAnimationName));
            currentFrame = animation.getKeyFrame(0);
        }
    }

    public void stopAnimation() {
        stateTime = 0;
        currentFrame = animation.getKeyFrame(0);
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void dispose() {
    }
}
