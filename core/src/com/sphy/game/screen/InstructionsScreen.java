package com.sphy.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.sphy.game.manager.ScoreManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InstructionsScreen implements Screen {

    private Stage stage;
    private Texture instructions;

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

        instructions = new Texture(Gdx.files.internal("textures/Instructions.png"));
        VisImage image = new VisImage(instructions);

        VisTextButton quitButton = new VisTextButton("RETURN TO MAIN MENU");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        table.row();
        table.add(image).center().width(500).height(200);

        table.row();
        table.add(createInstructionsTable()).center().padTop(20).row();

        table.add(quitButton).center().width(200).height(50).pad(5);
        table.row();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


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
        instructions.dispose();
    }

    private VisTable createInstructionsTable() {
        VisTable scoresTable = new VisTable();

        // Encabezado
        scoresTable.add(new VisLabel("Instructions of the X-Terminator")).colspan(2).center().padBottom(10).row();
        scoresTable.add(new VisLabel("BUTTON           ACTION          ")).colspan(2).center().padBottom(10).row();


        // Mostrar las instrucciones

        scoresTable.add(new VisLabel("Left button")).left();
        scoresTable.add(new VisLabel("  Move player to left")).left().row();
        scoresTable.add(new VisLabel("Right button")).left();
        scoresTable.add(new VisLabel("  Move player to right")).left().row();
        scoresTable.add(new VisLabel("Up button")).left();
        scoresTable.add(new VisLabel("  Jump")).left().row();
        scoresTable.add(new VisLabel("Space button")).left();
        scoresTable.add(new VisLabel("  Shut")).left().row();



        return scoresTable;
    }

}
