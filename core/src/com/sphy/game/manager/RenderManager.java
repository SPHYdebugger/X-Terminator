package com.sphy.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sphy.game.domain.Bullet;
import com.sphy.game.domain.Enemy;

public class RenderManager implements Disposable {

    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;

    private SpriteManager spriteManager;

    public RenderManager(SpriteManager spriteManager){
        this.spriteManager = spriteManager;
        initialize();
    }

    private void initialize(){
        batch = new SpriteBatch();
        font = new BitmapFont();
        backgroundTexture = new Texture(Gdx.files.internal("textures/background.png"));
    }

    public void draw(){
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        drawPlayer();
        drawEnemies();
        drawHud();
        batch.end();

        spriteManager.manageInput();
        spriteManager.handleCollisions();
    }

    private void drawPlayer(){
        spriteManager.player.draw(batch);
        spriteManager.bullet.draw(batch);
        for (Bullet bullet : spriteManager.bulletsL) {
            bullet.draw(batch);
        }
        for (Bullet bullet : spriteManager.bulletsR) {
            bullet.draw(batch);
        }
    }

    private void drawEnemies(){
        for (Enemy enemy : spriteManager.enemiesR)
            enemy.draw(batch);
        for (Enemy enemy : spriteManager.enemiesL)
            enemy.draw(batch);
    }

    private void drawHud(){
        font.draw(batch, "LIVES: " + spriteManager.player.lives, 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "SCORE: " + spriteManager.score, 20, Gdx.graphics.getHeight() - 50);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}
