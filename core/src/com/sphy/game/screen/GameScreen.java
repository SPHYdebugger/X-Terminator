package com.sphy.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.sphy.game.domain.Bullet;
import com.sphy.game.domain.Enemy;
import com.sphy.game.domain.Player;
import com.sphy.game.manager.*;


public class GameScreen implements Screen {

    SpriteManager spriteManager;
    RenderManager renderManager;
    LevelManager levelManager;
    CameraManager cameraManager;

    Stage stage;



    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        ResourceManager.loadAllResources();

        while (!ResourceManager.update()) {}

        spriteManager = new SpriteManager();
        spriteManager.setStage(stage);
        levelManager = new LevelManager(spriteManager);
        cameraManager = new CameraManager(spriteManager,levelManager);

        spriteManager.setCameraManager(cameraManager);
        levelManager.setCameraManager(cameraManager);
        levelManager.loadCurrentLevel();

        renderManager = new RenderManager(spriteManager, cameraManager, levelManager.batch);


    }



    @Override
    public void render(float dt) {
        cameraManager.handleCamera();
        spriteManager.update(dt);

        renderManager.draw();
        renderManager = new RenderManager(spriteManager,cameraManager, levelManager.batch);
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteManager.dispose();
        renderManager.dispose();

    }










}
