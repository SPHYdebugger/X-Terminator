package com.sphy.game.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.sphy.game.domain.Bullet;
import com.sphy.game.domain.Enemy;
import com.sphy.game.domain.Player;
import com.sphy.game.screen.GameOverMenuScreen;
import com.sphy.game.screen.MainMenuScreen;

public class SpriteManager implements Disposable {


    Player player;
    Bullet bullet;
    Sound gunSound;
    Sound hitSound;
    Sound boomSound;
    Sound jumpSound;
    Sound gameOverSound;
    Array<Enemy> enemiesR;
    Array<Enemy> enemiesL;
    float lastEnemyR;
    float lastEnemyL;
    Array<Bullet> bulletsR;
    Array<Bullet> bulletsL;
    boolean pause;

    long lastBulletTime;
    int playerDirection;
    float randomDelayR = MathUtils.random(2f, 4f) * 1000000000;
    float randomDelayL = MathUtils.random(2f, 4f) * 1000000000;
    int score =0;
    float enemyYdown = 135;
    float enemyYup = 285;


    public SpriteManager(){
        initialize();
    }
    public void initialize(){
        player = new Player(new Vector2(100, 232), "SofiDER");


        boomSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        gunSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.mp3"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        enemiesR = new Array<>();
        enemiesL = new Array<>();
        bulletsR = new Array<>();
        bulletsL = new Array<>();
        lastEnemyR = TimeUtils.nanoTime();
        lastEnemyL = TimeUtils.nanoTime();
        pause = false;
        lastBulletTime = TimeUtils.nanoTime();
        if (PreferencesManager.isSoundEnable())
            boomSound.play();
    }

    public void updateEnemies(){

    }

    public void updatePlayer(){

    }


    public void spawnEnemyR() {
        //crear un enemigo por la derecha
        int xR = Gdx.graphics.getWidth()*3;
        float y = MathUtils.randomBoolean() ? enemyYdown : enemyYup;

        Enemy enemyR = new Enemy(new Vector2(xR, y), "arana");
        enemiesR.add(enemyR);
        lastEnemyR = TimeUtils.nanoTime();
    }
    public void spawnEnemyL() {
        //crear un enemigo por la izquierda
        float y = MathUtils.randomBoolean() ? enemyYdown : enemyYup;
        int xL = 0;
        Enemy enemyL = new Enemy(new Vector2(xL, y), "arana");
        enemiesL.add(enemyL);
        lastEnemyL = TimeUtils.nanoTime();
    }
    private void spawnBullet() {
        //crear la bala según la orientación del jugador
        if (playerDirection == 1) {
            Bullet newBullet = new Bullet(new Vector2(player.position.x + player.texture.getWidth(), player.position.y + 140),"BulletR");
            bulletsR.add(newBullet);
        } else {
            Bullet newBullet = new Bullet(new Vector2(player.position.x, player.position.y + 140),"BulletL");
            bulletsL.add(newBullet);
        }
    }
    public void handleCollisions() {
        for (Enemy enemy : enemiesR) {
            //colision de un enemigo que viene por la derecha con el jugador
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();
                    gameOverScreen.setScore(score);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
                }
                enemiesR.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    hitSound.play();
            }
            //colision de un enemigo que viene por la derecha con la bala
            for (Bullet bullet : bulletsR) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesR.removeValue(enemy, true);
                    bulletsR.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        boomSound.play();
                }
            }
        }

        //colision de un enemigo que viene por la izquierda con el jugador
        for (Enemy enemy : enemiesL) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    gameOverSound.play();
                    GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();

                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {

                            ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
                            gameOverScreen.setScore(score);
                        }
                    }, 2);




                }
                enemiesL.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    hitSound.play();
            }
            //colision de un enemigo que viene por la izquierda con la bala
            for (Bullet bullet : bulletsL) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesL.removeValue(enemy, true);
                    bulletsL.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        boomSound.play();
                }
            }
        }
    }

    public void update(float dt){
        if (!pause) {
            //dibujar un enemigo por la derecha y moverlo
            if (TimeUtils.nanoTime() - lastEnemyR > randomDelayR)
                spawnEnemyR();
            for (Enemy enemy : enemiesR) {
                enemy.move(-7, 0);
                if (enemy.position.x < 0){
                    enemiesR.removeValue(enemy, true);
                }
            }
            //dibujar un enemigo por la izquierda y moverlo
            if (TimeUtils.nanoTime() - lastEnemyL > randomDelayL)
                spawnEnemyL();
            for (Enemy enemy : enemiesL) {
                enemy.move(7, 0);
                if (enemy.position.x > Gdx.graphics.getWidth()*3){
                    enemiesL.removeValue(enemy, true);
                }
            }



        }
        //mover las balas de la derecha
        for (Bullet bullet : bulletsR) {
            bullet.move(10, 0);
            if (bullet.position.x > player.position.x + player.texture.getWidth() +200){
                bulletsR.removeValue(bullet, true);
            }
        }
        //mover las balas de la izquierda
        for (Bullet bullet : bulletsL) {
            bullet.move(-10, 0);
            if (bullet.position.x < player.position.x -200){
                bulletsL.removeValue(bullet, true);
            }
        }
    }

    public void manageInput() {
        //moverse a la derecha
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerDirection = 1;

            player.texture = new Texture("textures/SofiDER.png");
            player.move(10,0);
        // moverse a la izquierda
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerDirection = 0;
            player.texture = new Texture("textures/SofiIZQ.png");
            player.move(-10,0);

        }
        //Saltar
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (player.position.y == 32) {
                player.move(0,200);
                jumpSound.play();
            }
        }
        // caer poco a poco
        if (player.position.y > 32) {
            player.move(0,-10);
        }


        //Disparar
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            long currentTime = TimeUtils.nanoTime();
            if (currentTime - lastBulletTime > 300000000) { // 0.3 segundos en nanosegundos
                spawnBullet();
                gunSound.play();
                lastBulletTime = currentTime;
            }
        }

        //salir al menu
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
        }

        //pausar el juego
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
    }

    @Override
    public void dispose() {
        //liberar la memoria
        player.dispose();
        bullet.dispose();
    }
}
