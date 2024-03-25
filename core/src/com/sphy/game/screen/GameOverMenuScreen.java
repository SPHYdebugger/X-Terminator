package com.sphy.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImage;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class GameOverMenuScreen implements Screen {

    private Stage stage;
    Sound initSound;
    Preferences prefs;
    Texture gameOver;



    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        float width = Gdx.graphics.getWidth() * 0.5f;
        float height = Gdx.graphics.getHeight() * 0.5f;

        float x = (Gdx.graphics.getWidth() - width) / 2;
        float y = (Gdx.graphics.getHeight() - height) / 2;

        VisTable table = new VisTable(true);
        table.setSize(width, height);
        table.setPosition(x, y);



        stage.addActor(table);
        initSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameOver.wav"));
        prefs = Gdx.app.getPreferences("GamePreferences");
        if (prefs.getBoolean("sound")){
            initSound.play();
        }

        VisImage image = new VisImage(new Texture(Gdx.files.internal("textures/Game-over.png")));


        VisTextButton playButton = new VisTextButton("PLAY AGAIN");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a jugar
                dispose();
                initSound.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });

        VisTextButton configButton = new VisTextButton("VIEW SCORES");
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de configuración
                dispose();
                initSound.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreferenceScreen());
            }
        });

        VisTextButton quitButton = new VisTextButton("RETURN TO MAIN MENU");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                initSound.stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        VisLabel aboutLabel = new VisLabel("YOUR SCORE");

        VisLabel musicLabel = new VisLabel("1000");


        table.row();
        table.add(image).center().width(500).height(200);
        table.row();
        table.add(playButton).center().width(200).height(100).pad(5);
        table.row();
        table.add(configButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(quitButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(aboutLabel).center().width(200).height(20).pad(5);
        table.row();
        table.add(musicLabel).center().width(200).height(20).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Pinta la UI en la pantalla
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
        stage.dispose();
    }
}
