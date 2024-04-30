package com.sphy.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sphy.game.manager.CameraManager;
import com.sphy.game.manager.LevelManager;
import com.sphy.game.manager.RenderManager;
import com.sphy.game.manager.SpriteManager;


public class GameScreen implements Screen {

    SpriteManager spriteManager;
    RenderManager renderManager;
    LevelManager levelManager;
    CameraManager cameraManager;
    String playerNameText = "Anonymous";

    Stage stage;
    private boolean paused = false;

    public GameScreen(String playerNameText) {
        this.playerNameText = playerNameText;
    }

    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);




        spriteManager = new SpriteManager();
        spriteManager.setStage(stage);
        levelManager = new LevelManager(spriteManager);
        cameraManager = new CameraManager(spriteManager,levelManager);
        spriteManager.setPlayerNameText(playerNameText);
        spriteManager.setCameraManager(cameraManager);
        levelManager.setCameraManager(cameraManager);
        levelManager.loadCurrentLevel();

        renderManager = new RenderManager(spriteManager, cameraManager, levelManager.batch, playerNameText);


    }



    @Override
    public void render(float dt) {

        cameraManager.handleCamera();
        spriteManager.update(dt);

        renderManager.draw();
        renderManager = new RenderManager(spriteManager,cameraManager, levelManager.batch, playerNameText);
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
