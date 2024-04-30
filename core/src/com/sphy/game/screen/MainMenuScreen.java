package com.sphy.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.sphy.game.manager.PreferencesManager;
import com.sphy.game.manager.ResourceManager;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private TextField playerName;
    private String playerNameText;

    public String getPlayerNameText() {
        return playerNameText;
    }

    public void setPlayerNameText(String playerNameText) {
        this.playerNameText = playerNameText;
    }

    @Override
    public void show() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

        if (PreferencesManager.isSoundEnable()){
            ResourceManager.getMp3Sound("popdance").play();
        }

        VisTextButton playButton = new VisTextButton("PLAY");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a jugar
                dispose();
                ResourceManager.getMp3Sound("popdance").stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(playerNameText));
            }
        });

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        BitmapFont font = new BitmapFont();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        playerName = new TextField("", textFieldStyle);
        playerName.setMessageText("Enter your name");
        playerName.setColor(Color.WHITE);

        playerName.addListener(new InputListener() {
            public boolean keyTyped(InputEvent event, char character) {
                if (character == '\r' || character == '\n') { // Enter
                    playerNameText = playerName.getText();
                    System.out.println(playerNameText);
                    return true;
                }
                return false;
            }
        });

        VisTextButton configButton = new VisTextButton("CONFIGURATION");
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de configuración
                dispose();
                ResourceManager.getMp3Sound("popdance").stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PreferenceScreen());
            }
        });

        VisTextButton quitButton = new VisTextButton("QUIT");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                System.exit(0);
            }
        });

        VisLabel aboutLabel = new VisLabel("libGDX(c)\n Game by Santiago Pérez");

        VisLabel musicLabel = new VisLabel("Music I Use: Bensound.com/royalty-free-music\n" +
                "License code: BFZ3QBQDHC9HCJ9A");




        table.row();
        table.add(playButton).center().width(200).height(100).pad(5);
        table.row();
        table.add(playerName).width(200).height(50);
        table.row();
        table.add(configButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(quitButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(aboutLabel).center().width(200).height(20).pad(5);
        table.row();
        table.add().left().width(200).height(20).pad(90);
        table.row();
        table.add(musicLabel).center().width(200).height(20).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
