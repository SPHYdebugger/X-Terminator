package com.sphy.game.manager2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sphy.game.domain.Bullet;
import com.sphy.game.domain.Enemy;

public class RenderManager2 implements Disposable {

    Batch batch;
    BitmapFont font;
    Texture backgroundTexture;

    private SpriteManager2 spriteManager2;
    CameraManager2 cameraManager2;

    public RenderManager2(SpriteManager2 spriteManager2, CameraManager2 cameraManager2, Batch batch){
        this.spriteManager2 = spriteManager2;
        this.cameraManager2 = cameraManager2;
        this.batch = batch;
        initialize();
    }

    private void initialize(){

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        //backgroundTexture = new Texture(Gdx.files.internal("textures/background.png"));
    }

    public void draw(){

        batch.begin();
        //batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //drawHud();
        drawPlayer();
        //drawEnemies();
        //drawTiledEnemies();

        batch.end();

        spriteManager2.manageInput();
        //spriteManager2.handleCollisions();
    }

    private void drawPlayer(){
        spriteManager2.player.draw(batch);

        for (Bullet bullet : spriteManager2.bulletsL) {
            bullet.draw(batch);
        }
        for (Bullet bullet : spriteManager2.bulletsR) {
            bullet.draw(batch);
        }
    }

    private void drawEnemies(){
        for (Enemy enemy : spriteManager2.enemiesR)
            enemy.draw(batch);
        for (Enemy enemy : spriteManager2.enemiesL)
            enemy.draw(batch);
    }

    private void drawTiledEnemies(){
        for (Enemy enemy : spriteManager2.enemiesRTiled)
            enemy.draw(batch);
        for (Enemy enemy : spriteManager2.enemiesLTiled)
            enemy.draw(batch);
    }
    private void drawHud(){
        font.draw(batch, " LIVES:  " + spriteManager2.player.lives, cameraManager2.camera.position.x - 500 , cameraManager2.camera.position.y + 200);
        font.draw(batch, " SCORE:  " + spriteManager2.score, cameraManager2.camera.position.x - 300 , cameraManager2.camera.position.y + 200);
        //font.draw(batch, "LIVES: " + spriteManager.player.lives, 20, Gdx.graphics.getHeight() - 20);
        //font.draw(batch, "SCORE: " + spriteManager.score, 20, Gdx.graphics.getHeight() - 50);
    }



    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}
