package com.sphy.game.manager2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.sphy.game.domain.Bullet;
import com.sphy.game.domain.Enemy;
import com.sphy.game.domain.FinalEnemy;
import com.sphy.game.domain.Player;
import com.sphy.game.items.Goal;
import com.sphy.game.items.Stone;
import com.sphy.game.manager.PreferencesManager;
import com.sphy.game.screen.GameOverMenuScreen;
import com.sphy.game.screen.GameScreen;
import com.sphy.game.screen.GameScreen2;
import com.sphy.game.screen.MainMenuScreen;

import static com.badlogic.gdx.math.MathUtils.random;

public class SpriteManager2 implements Disposable {


    Player player;
    Bullet bullet2;
    Sound gunSound;
    Sound hitSound;
    Sound boomSound;
    Sound jumpSound;
    Sound gameOverSound;
    Array<Enemy> enemiesR2;
    Array<Enemy> enemiesL2;



    FinalEnemy enemyTiled;

    Array<Stone> stones;
    float lastEnemyR2;
    float lastEnemyL2;
    Array<Bullet> bulletsR2;
    Array<Bullet> bulletsL2;
    boolean pause;
    boolean noMoveRigth;
    boolean noMoveLeft;
    boolean isOnGround;
    long lastBulletTime;
    int playerDirection;
    float randomDelayR = random(2f, 4f) * 1000000000;
    float randomDelayL = random(2f, 4f) * 1000000000;
    int score;
    float speed = 2;
    float enemyYdown = 125;
    float enemyYup = 385;

    private LevelManager2 levelManager2;
    CameraManager2 cameraManager2;
    private Goal goal;


    public SpriteManager2(Player player){
        this.player = player;
        initialize();
    }

    public void setCameraManager(CameraManager2 cameraManager) {
        this.cameraManager2 = cameraManager;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setEnemyTiled(FinalEnemy enemyTiled) {
        this.enemyTiled = enemyTiled;
    }

    public void initialize(){
        player.position.x= 100;
        player.rect.x=100;
        player.position.y= 32;
        player.rect.y= 32;
        score= player.getScore();
        boomSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        gunSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.mp3"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        enemiesR2 = new Array<>();
        enemiesL2 = new Array<>();


        stones = new Array<>();
        bulletsR2 = new Array<>();
        bulletsL2 = new Array<>();
        lastEnemyR2 = TimeUtils.nanoTime();
        lastEnemyL2 = TimeUtils.nanoTime();
        pause = false;
        noMoveRigth = false;
        noMoveLeft = false;
        isOnGround = player.position.y == 32;
        lastBulletTime = TimeUtils.nanoTime();
        if (PreferencesManager.isSoundEnable())
            boomSound.play();
    }


    public void spawnEnemyR() {
        //crear un enemigo por la derecha
        int xR = Gdx.graphics.getWidth()*3;
        float y = MathUtils.randomBoolean() ? enemyYdown : enemyYup;

        Enemy enemyR = new Enemy(new Vector2(xR, y), "arana");
        enemiesR2.add(enemyR);

        lastEnemyR2 = TimeUtils.nanoTime();
    }
    public void spawnEnemyL() {
        //crear un enemigo por la izquierda
        float y = MathUtils.randomBoolean() ? enemyYdown : enemyYup;
        int xL = 0;
        Enemy enemyL = new Enemy(new Vector2(xL, y), "arana");
        enemiesL2.add(enemyL);
        lastEnemyL2 = TimeUtils.nanoTime();
    }

    public void updateEnemies(float dt){
        //Mover enemigos de la derecha de Tiled

            if (cameraManager2.camera.frustum.pointInFrustum(enemyTiled.position.x, enemyTiled.position.y, 0)){
                enemyTiled.update(dt);
            }




        //dibujar un enemigo por la derecha y moverlo
        if (TimeUtils.nanoTime() - lastEnemyR2 > randomDelayR)
            spawnEnemyR();
            for (Enemy enemy : enemiesR2) {
                enemy.move(-5, 0);
                if (enemy.position.x < 0){
                    enemiesR2.removeValue(enemy, true);
                }
            }



        //dibujar un enemigo por la izquierda y moverlo
        if (TimeUtils.nanoTime() - lastEnemyL2 > randomDelayL)
            spawnEnemyL();
            for (Enemy enemy : enemiesL2) {
                enemy.move(5, 0);
                if (enemy.position.x > Gdx.graphics.getWidth()){
                    enemiesL2.removeValue(enemy, true);
                }
            }

    }

    public void updatePlayer(){}

    private void spawnBullet() {
        //crear la bala según la orientación del jugador
        if (playerDirection == 1) {
            Bullet newBullet = new Bullet(new Vector2(player.position.x + player.currentFrame.getRegionWidth(), player.position.y + 140),"BulletR");
            bulletsR2.add(newBullet);
        } else {
            Bullet newBullet = new Bullet(new Vector2(player.position.x, player.position.y + 140),"BulletL");
            bulletsL2.add(newBullet);
        }
    }

    public void updateBullets(){
        //mover las balas de la derecha
        for (Bullet bullet : bulletsR2) {
            bullet.move(10, 0);
            if (bullet.position.x > Gdx.graphics.getWidth()*2){
                bulletsR2.removeValue(bullet, true);
            }
        }
        //mover las balas de la izquierda
        for (Bullet bullet : bulletsL2) {
            bullet.move(-10, 0);
            if (bullet.position.x < 0){
                bulletsL2.removeValue(bullet, true);
            }
        }
    }





    public void handleCollisions() {
        for (Enemy enemy : enemiesR2) {
            //colision de un enemigo que viene por la derecha con el jugador
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();
                    gameOverScreen.setScore(score);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
                }
                enemiesR2.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    hitSound.play();
            }
            //colision de un enemigo que viene por la derecha con la bala
            for (Bullet bullet : bulletsR2) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesR2.removeValue(enemy, true);
                    bulletsR2.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        boomSound.play();
                }
            }
        }

        //colision de un enemigo que viene por la izquierda con el jugador
        for (Enemy enemy : enemiesL2) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    if (PreferencesManager.isSoundEnable())
                        gameOverSound.play();

                    GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();
                    gameOverScreen.setScore(score);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {

                            ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
                            gameOverScreen.setScore(score);
                        }
                    }, 2);




                }
                enemiesL2.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    hitSound.play();
            }
            //colision de un enemigo que viene por la izquierda con la bala
            for (Bullet bullet : bulletsL2) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesL2.removeValue(enemy, true);
                    bulletsL2.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        boomSound.play();
                }
            }
        }


        //colision de un enemigoTiled que viene por la derecha con el jugador
        if (enemyTiled.rect.overlaps(player.rect)) {
            player.lives--;
            if (player.lives == 0) {
                pause = true;
                GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();
                gameOverScreen.setScore(score);
                ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
            }

            if (PreferencesManager.isSoundEnable())
                hitSound.play();
        }

        for (Bullet bullet : bulletsR2) {
            if (enemyTiled.rect.overlaps(bullet.rect)) {
                score += 300;
                enemyTiled.damage = enemyTiled.damage-100;
                bulletsR2.removeValue(bullet, true);
                if (enemyTiled.damage == 0) {
                    pause = true;
                    GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();
                    gameOverScreen.setScore(score);
                    ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
                }
                if (PreferencesManager.isSoundEnable())
                    boomSound.play();
            }
        }






        for (Stone stone : stones){
            Rectangle stoneRect = new Rectangle(stone.getX(), stone.getY(), stone.getWidth(), stone.getHeigth());

            if (player.rect.overlaps(stoneRect) && player.position.y >= stone.getY()) {
                player.position.y = 192;

                player.rect.y = 192;
            }

        }
        /*Rectangle goalRect = new Rectangle(goal.getX(),goal.getY(), goal.getWidth(), goal.getHeigth());
        if (player.rect.overlaps(goalRect)){
            pause= true;
            showVictoryMessage();
        }*/




    }

    public void update(float dt){
        if (!pause) {
            updateEnemies(dt);

        }
        updateBullets();
    }

    public void manageInput() {
        boolean isMoving = false;

        //moverse a la derecha
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerDirection = 1;
            player.changeAnimation("SofiDER");
            if (!noMoveRigth){
                player.move(10,0);
                isMoving = true;
            }
            // moverse a la izquierda
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerDirection = 0;
            player.changeAnimation("SofiIZQ");
            if (!noMoveLeft){
                player.move(-10,0);
                isMoving = true;
            }
        }

        //Saltar
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (player.position.y == 32) {
                player.move(0,300);
                if (PreferencesManager.isSoundEnable())
                    jumpSound.play();

            }
            if (player.position.y == 192) {
                player.move(0,150);
                if (PreferencesManager.isSoundEnable())
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
                if (PreferencesManager.isSoundEnable())
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

        // Si no se está moviendo, detener la animación
        if (!isMoving) {
            player.stopAnimation();
        }
    }

    @Override
    public void dispose() {
        //liberar la memoria
        player.dispose();
        bullet2.dispose();
    }

    private void showVictoryMessage() {

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();

        // Crea la etiqueta del mensaje
        Label victoryLabel = new Label("¡Fase superada!", style);
        victoryLabel.setPosition(Gdx.graphics.getWidth() / 2 - victoryLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        // Agrega la etiqueta al Stage


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverMenuScreen());
            }
        }, 3);
    }

}
