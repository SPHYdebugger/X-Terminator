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


public class GameScreen implements Screen {

    SpriteBatch batch;
    Player player;
    Bullet bullet;
    Sound initSound;
    private Texture backgroundTexture;
    Array<Enemy> enemiesR;
    Array<Enemy> enemiesL;
    float lastEnemyR;
    float lastEnemyL;

    Preferences prefs;
    BitmapFont font;
    boolean pause;
    Sound gunSound;
    long lastBulletTime;
    int playerDirection;
    float randomDelayR = MathUtils.random(2f, 4f) * 1000000000;
    float randomDelayL = MathUtils.random(2f, 4f) * 1000000000;


    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player(new Texture("textures/sofiSoldadoTra.png"), new Vector2(0, 0));
        bullet = new Bullet(new Texture("textures/balaDER.png"), new Vector2(-100, -100));
        backgroundTexture = new Texture(Gdx.files.internal("textures/background.png"));
        initSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        //gunSound = Gdx.audio.newSound(Gdx.files.internal("sounds/disparo.wav"));
        enemiesR = new Array<>();
        enemiesL = new Array<>();
        lastEnemyR = TimeUtils.nanoTime();
        lastEnemyL = TimeUtils.nanoTime();

        font = new BitmapFont();
        pause = false;

        lastBulletTime = TimeUtils.nanoTime();


        prefs = Gdx.app.getPreferences("GamePreferences");
        if (prefs.getBoolean("sound"))
            initSound.play();
    }



    @Override
    public void render(float dt) {
        ScreenUtils.clear(1, 0, 0, 1);

        // Lógica
        if (!pause) {
            if (TimeUtils.nanoTime() - lastEnemyR > randomDelayR)
                spawnEnemyR();
            for (Enemy enemy : enemiesR) {
                enemy.move(-10, 0);
                if (enemy.position.x < 0){
                    enemiesR.removeValue(enemy, true);
                }
            }
            if (TimeUtils.nanoTime() - lastEnemyL > randomDelayL)
                spawnEnemyL();
            for (Enemy enemy : enemiesL) {
                enemy.move(10, 0);
                if (enemy.position.x > Gdx.graphics.getWidth()){
                    enemiesL.removeValue(enemy, true);
                }
            }
            if (playerDirection == 1){
                bullet.move(10,0);
            } else {
                bullet.move(-10, 0);
            }







        }

        // Render
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.draw(batch);
        bullet.draw(batch);
        manageInput();
        for (Enemy enemy : enemiesR)
            enemy.draw(batch);
        for (Enemy enemy : enemiesL)
            enemy.draw(batch);
        font.draw(batch, "Vidas: " + player.lives, 20, Gdx.graphics.getHeight() - 20);
        handleCollisions();
        batch.end();








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
        bullet.dispose();
    }



    public void manageInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerDirection = 1;
            player.texture = new Texture("textures/SofiDER.png");

            player.move(10,0);

        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerDirection = 0;
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
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {

            if (playerDirection == 1){
                bullet.position.set(player.position.x + player.texture.getWidth(), player.position.y + 220);
                bullet.rect.setPosition(bullet.position);
            } else {
                bullet.position.set(player.position.x, player.position.y + 220);
                bullet.rect.setPosition(bullet.position);
            }





        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }


    public void spawnEnemyR() {
        int x = Gdx.graphics.getWidth();
        int y = 210;
        Enemy enemyR = new Enemy(new Texture("textures/araña.png"), new Vector2(x, y));
        enemiesR.add(enemyR);
        lastEnemyR = TimeUtils.nanoTime();
    }
    public void spawnEnemyL() {
        int y = 210;
        int xL = 0;
        Enemy enemyL = new Enemy(new Texture("textures/araña.png"), new Vector2(xL, y));
        enemiesL.add(enemyL);
        lastEnemyL = TimeUtils.nanoTime();
    }

    private void handleCollisions() {
        for (Enemy enemy : enemiesR) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverMenuScreen());
                }
                enemiesR.removeValue(enemy, true);
                if (prefs.getBoolean("sound"))
                    initSound.play();
            }

            if (enemy.rect.overlaps(bullet.rect)) {


                enemiesR.removeValue(enemy, true);
                if (prefs.getBoolean("sound"))
                    initSound.play();
                bullet.position.set(-100, -100);
                bullet.rect.setPosition(bullet.position);
            }
        }

        for (Enemy enemy : enemiesL) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverMenuScreen());
                }
                enemiesL.removeValue(enemy, true);
                if (prefs.getBoolean("sound"))
                    initSound.play();
            }

            if (enemy.rect.overlaps(bullet.rect)) {


                enemiesL.removeValue(enemy, true);
                if (prefs.getBoolean("sound"))
                    initSound.play();
                bullet.position.set(-100, -100);
                bullet.rect.setPosition(bullet.position);
            }
        }

    }



}
