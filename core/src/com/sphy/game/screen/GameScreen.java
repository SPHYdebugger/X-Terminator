package com.sphy.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.sphy.game.domain.Bullet;
import com.sphy.game.domain.Enemy;
import com.sphy.game.domain.Player;
import com.sphy.game.manager.PreferencesManager;
import com.sphy.game.manager.RenderManager;
import com.sphy.game.manager.SpriteManager;


public class GameScreen implements Screen {

    SpriteManager spriteManager;
    RenderManager renderManager;




    @Override
    public void show() {
        spriteManager = new SpriteManager();
        renderManager = new RenderManager(spriteManager);
    }



    @Override
    public void render(float dt) {
        spriteManager.update(dt);
        renderManager.draw();

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
