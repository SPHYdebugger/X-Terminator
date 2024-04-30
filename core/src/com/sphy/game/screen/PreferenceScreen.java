package com.sphy.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.*;
import com.sphy.game.manager.PreferencesManager;

public class PreferenceScreen implements Screen {

    Stage stage;


    private void loadScreen() {
        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        table.center();

        VisLabel title = new VisLabel("SETTINGS");
        title.setFontScale(2.5f);

        final VisCheckBox checkSound = new VisCheckBox("SOUND");
        checkSound.setChecked(PreferencesManager.isSoundEnable());
        checkSound.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PreferencesManager.switchSound(checkSound.isChecked());
            }
        });

        VisLabel difficultyLabel = new VisLabel("-- DIFFICULTY --");

        String[] resolutionsArray = {"LOW", "HIGH"};
        final VisList difficultyList = new VisList();
        difficultyList.setItems(resolutionsArray);
        difficultyList.setSelected(PreferencesManager.getDifficulty());
        difficultyList.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                switch (difficultyList.getSelectedIndex()) {
                    case 0:
                        PreferencesManager.setDifficulty("LOW");
                        break;
                    case 1:
                        PreferencesManager.setDifficulty("HIGH");
                        break;
                    default:
                }
            }
        });

        VisTextButton exitButton = new VisTextButton("MAIN MENU");
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                PreferencesManager.save();
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        table.row().height(150);
        table.add(title).center().pad(35f);
        table.row().height(20);
        table.add(checkSound).center().pad(5f);
        table.row().height(20);
        table.add(difficultyLabel).center().pad(5f);
        table.row().height(70);
        table.add(difficultyList).center().pad(5f);
        table.row().height(70);
        table.add(exitButton).center().width(300).pad(5f);
        table.row().height(70);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void loadPreferences() {
        if (!PreferencesManager.existSound())
            PreferencesManager.enableSound();
    }

    @Override
    public void show() {
        loadPreferences();
        loadScreen();
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
    }
}
