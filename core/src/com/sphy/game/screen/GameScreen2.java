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
import com.sphy.game.manager2.CameraManager2;
import com.sphy.game.manager2.LevelManager2;
import com.sphy.game.manager2.RenderManager2;
import com.sphy.game.manager2.SpriteManager2;


public class GameScreen2 implements Screen {

    SpriteManager2 spriteManager2;
    RenderManager2 renderManager2;
    LevelManager2 levelManager2;
    CameraManager2 cameraManager2;





    @Override
    public void show() {






        spriteManager2 = new SpriteManager2();

        levelManager2 = new LevelManager2(spriteManager2);
        cameraManager2 = new CameraManager2(spriteManager2,levelManager2);

        spriteManager2.setCameraManager(cameraManager2);
        levelManager2.setCameraManager(cameraManager2);
        levelManager2.loadCurrentLevel2();

        renderManager2 = new RenderManager2(spriteManager2, cameraManager2, levelManager2.batch);
    }



    @Override
    public void render(float dt) {
        cameraManager2.handleCamera();
        spriteManager2.update(dt);

        renderManager2.draw();
        renderManager2 = new RenderManager2(spriteManager2,cameraManager2, levelManager2.batch);

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
        spriteManager2.dispose();
        renderManager2.dispose();

    }










}
