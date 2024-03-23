package com.sphy.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.sphy.game.domain.Enemy;
import com.sphy.game.domain.Player;


public class GameScreen implements Screen {

    SpriteBatch batch;
    Player player;
    Sound initSound;
    private Texture backgroundTexture;
    Array<Enemy> enemies;
    float lastEnemy;
    float timeBetweenEnemies;
    Preferences prefs;
    BitmapFont font;
    boolean pause;
    Sound gunSound;



    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player(new Texture("textures/sofiSoldadoTra.png"), new Vector2(0, 0));
        backgroundTexture = new Texture(Gdx.files.internal("textures/background.png"));
        initSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        //gunSound = Gdx.audio.newSound(Gdx.files.internal("sounds/disparo.wav"));
        enemies = new Array<>();
        lastEnemy = TimeUtils.nanoTime();
        timeBetweenEnemies = 1000000000;
        font = new BitmapFont();
        pause = false;


        prefs = Gdx.app.getPreferences("GamePreferences");
        if (prefs.getBoolean("sound"))
            initSound.play();
    }



    @Override
    /*public void render(float dt) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        manageInput();

        batch.begin();
        batch.draw(player.texture, player.position.x, player.position.y, 150, 300);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }




    }*/
    public void render(float dt) {
        ScreenUtils.clear(1, 0, 0, 1);

        // Lógica
        if (!pause) {
            if (TimeUtils.nanoTime() - lastEnemy > timeBetweenEnemies)
                spawnEnemy();
            for (Enemy enemy : enemies) {
                enemy.move(-10, 0);
            }
            manageInput();
            handleCollisions();
        }

        // Render
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.draw(batch);
        for (Enemy enemy : enemies)
            enemy.draw(batch);
        font.draw(batch, "Vidas: " + player.lives, 20, Gdx.graphics.getHeight() - 20);
        batch.end();

        // Input
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
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



    public void manageInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.texture = new Texture("textures/SofiDER.png");

            player.move(10,0);

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.texture = new Texture("textures/SofiIZQ.png");
            player.move(-10,0);

        }else {
            player.texture = new Texture("textures/sofiSoldadoTra.png");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (player.position.y == 0) {
                player.move(0,200);
            }
        }
        // Simular la gravedad
        if (player.position.y > 0) {
            player.move(0,-5);
        }
    }


    public void spawnEnemy() {
        int x = Gdx.graphics.getWidth();
        int y = MathUtils.random(0, Gdx.graphics.getHeight());
        Enemy enemy = new Enemy(new Texture("textures/araña.png"), new Vector2(x, y));
        enemies.add(enemy);

        lastEnemy = TimeUtils.nanoTime();
    }

    private void handleCollisions() {
        for (Enemy enemy : enemies) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                        }
                    }, 2);
                }
                enemies.removeValue(enemy, true);
                if (prefs.getBoolean("sound"))
                    initSound.play();
            }
        }
    }

}
