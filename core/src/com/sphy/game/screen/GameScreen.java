package com.sphy.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sphy.game.domain.Enemy;
import com.sphy.game.domain.Player;


public class GameScreen implements Screen {

    SpriteBatch batch;
    Player player;
    Sound initSound;
    private Texture backgroundTexture;


    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player(new Texture("textures/sofiSoldadoTra.png"), new Vector2(0, 0));
        backgroundTexture = new Texture(Gdx.files.internal("textures/background.png"));
        initSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        Preferences prefs = Gdx.app.getPreferences("GamePreferences");
        if (prefs.getBoolean("sound"))
            initSound.play();
    }



    @Override
    public void render(float dt) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        player.manageInput();

        batch.begin();
        batch.draw(player.texture, player.position.x, player.position.y);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }
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
        batch.dispose();
        backgroundTexture.dispose();
        player.dispose();
    }
}
