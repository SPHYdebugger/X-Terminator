package com.sphy.game.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.sphy.game.manager.ResourceManager;

public class FinalEnemy implements Disposable {
    public Texture texture;
    public Vector2 position;

    private Animation<TextureRegion> animation;
    private float stateTime;
    private TextureRegion currentFrame;
    public Rectangle rect;
    private float angle; // Ángulo actual del movimiento circular
    private float radius; // Radio del círculo
    private float speed; // Velocidad angular
    public int damage;

    public FinalEnemy(Vector2 center, String animationName, float radius, float speed) {
        this.position = new Vector2(center.x + radius, center.y);
        this.angle = 0;
        this.radius = radius;
        this.speed = speed;

        animation = new Animation<>(0.15f, ResourceManager.getAnimation(animationName));
        currentFrame = animation.getKeyFrame(0);

        rect = new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        damage= 1000;

    }

    public void draw(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }

    public void update(float deltaTime) {
        // Incrementa el ángulo basado en la velocidad angular y el tiempo transcurrido
        angle += speed * deltaTime;

        // Calcula las nuevas coordenadas basadas en el ángulo y el radio
        float x = position.x + radius * MathUtils.cos(angle);
        float y = position.y + radius * MathUtils.sin(angle);

        // Actualiza la posición del enemigo
        position.set(x, y);
        rect.x = x;
        rect.y = y;
    }



    @Override
    public void dispose() {
    }


}



