package com.sphy.game.manager;

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

public class RenderManager implements Disposable {

    Batch batch;
    BitmapFont font;
    Texture backgroundTexture;

    private SpriteManager spriteManager;
    CameraManager cameraManager;
    String playerNameText = ".";


    public RenderManager(SpriteManager spriteManager, CameraManager cameraManager, Batch batch, String playerNameText){
        this.spriteManager = spriteManager;
        this.cameraManager = cameraManager;
        this.batch = batch;
        this.playerNameText = playerNameText;
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
        drawHud();
        drawPlayer();
        drawEnemies();
        drawTiledEnemies();

        batch.end();

        spriteManager.manageInput();
        spriteManager.handleCollisions();
    }

    private void drawPlayer(){
        spriteManager.player.draw(batch);

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

    private void drawTiledEnemies(){
        for (Enemy enemy : spriteManager.enemiesRTiled)
            enemy.draw(batch);
        for (Enemy enemy : spriteManager.enemiesLTiled)
            enemy.draw(batch);
    }
    private void drawHud(){
        font.draw(batch, " LIVES:  " + spriteManager.player.lives, cameraManager.camera.position.x - 500 , cameraManager.camera.position.y + 200);
        font.draw(batch, " SCORE:  " + spriteManager.score, cameraManager.camera.position.x - 300 , cameraManager.camera.position.y + 200);
        //font.draw(batch, "LIVES: " + spriteManager.player.lives, 20, Gdx.graphics.getHeight() - 20);
        //font.draw(batch, "SCORE: " + spriteManager.score, 20, Gdx.graphics.getHeight() - 50);
        font.draw(batch, " PLAYER:  " + playerNameText, cameraManager.camera.position.x - 100 , cameraManager.camera.position.y + 200);

    }



    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}
