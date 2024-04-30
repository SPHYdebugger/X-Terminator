package com.sphy.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.sphy.game.manager.ResourceManager;

public class SplashScreen implements Screen {

    private final Texture splashTexture;
    private final Image splashImage;
    private boolean imageLoaded = false;
    private Stage stage;


    public SplashScreen() {
        splashTexture = new Texture(Gdx.files.internal("textures/splashImage.jpg"));
        splashImage = new Image(splashTexture);
        stage = new Stage();
    }

    @Override
    public void show( ) {
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        //Mostrar la imagen de SplashScreen
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
                Actions.delay(1.5f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        imageLoaded = true;
                    }
                })
        ));
        table.row().height(splashTexture.getHeight());
        table.add(splashImage).center();
        stage.addActor(table);

        //Cargar los recursos
       ResourceManager.loadAllResources();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (ResourceManager.update()) {
            if (imageLoaded) {
                //Si terminó de cargar la imagen, se inicia la mainMenuScreen
                MainMenuScreen mainMenuScreen = new MainMenuScreen();
                ((Game) Gdx.app.getApplicationListener()).setScreen(mainMenuScreen);
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        splashTexture.dispose();
        stage.dispose();
    }
}
