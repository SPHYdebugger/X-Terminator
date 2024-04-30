package com.sphy.game.manager;

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
import com.sphy.game.domain.Player;
import com.sphy.game.items.Goal;
import com.sphy.game.items.Stone;
import com.sphy.game.screen.GameOverMenuScreen;
import com.sphy.game.screen.GameScreen;
import com.sphy.game.screen.GameScreen2;
import com.sphy.game.screen.MainMenuScreen;

public class SpriteManager implements Disposable {


    Player player;
    int playerDirection;
    static String playerNameText = "";
    static int score =0;



    Bullet bullet;
    Array<Bullet> bulletsR;
    Array<Bullet> bulletsL;
    long lastBulletTime;


    Array<Enemy> enemiesR;
    Array<Enemy> enemiesL;
    Array<Enemy> enemiesRTiled;
    Array<Enemy> enemiesLTiled;
    float lastEnemyR;
    float lastEnemyL;
    float randomDelayR = MathUtils.random(2f, 4f) * 1000000000;
    float randomDelayL = MathUtils.random(2f, 4f) * 1000000000;
    float enemyYdown = 135;
    float enemyYup = 370;


    Array<Stone> stones;
    private Goal goal;


    boolean pause;
    boolean noMoveRigth;
    boolean noMoveLeft;
    boolean isOnGround;



    private LevelManager levelManager;
    CameraManager cameraManager;

    Stage stage;

    private float victoryMessageDuration = 3f;
    private float elapsedTime = 0f;
    private boolean victoryMessageDisplayed = false;





    public SpriteManager(){

        initialize();
    }

    public void setPlayerNameText(String playerNameText) {
        this.playerNameText = playerNameText;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        player = new Player(new Vector2(100, 232), "sofiSoldado");
        isOnGround = player.position.y == 32;

        enemiesR = new Array<>();
        enemiesL = new Array<>();
        enemiesRTiled = new Array<>();
        enemiesLTiled = new Array<>();

        stones = new Array<>();

        bulletsR = new Array<>();
        bulletsL = new Array<>();
        lastBulletTime = TimeUtils.nanoTime();

        lastEnemyR = TimeUtils.nanoTime();
        lastEnemyL = TimeUtils.nanoTime();

        pause = false;
        noMoveRigth = false;
        noMoveLeft = false;
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

    public void updateEnemies() {

        //Mover enemigos de la derecha de Tiled
        for (Enemy enemy : enemiesRTiled) {
            if (!cameraManager.camera.frustum.pointInFrustum(enemy.position.x, enemy.position.y, 0)) {
                continue;
            }
            enemy.move(-5, 0);
        }

        //dibujar un enemigo por la derecha y moverlo
        if (PreferencesManager.getDifficulty().equals("HIGH")) {
            if (TimeUtils.nanoTime() - lastEnemyR > randomDelayR){
                spawnEnemyR();
            }
            // Mover los enemigos y eliminar los que estén fuera de la pantalla
            for (Enemy enemy : enemiesR) {
                enemy.move(-5, 0);
                if (enemy.position.x < 0) {
                    enemiesR.removeValue(enemy, true);
                }
            }
        }


        //Mover enemigos de la izquierda de Tiled
        for (Enemy enemy : enemiesLTiled) {

            enemy.move(5, 0);
        }


        //dibujar un enemigo por la izquierda y moverlo
        if (PreferencesManager.getDifficulty().equals("HIGH")) {
            if (TimeUtils.nanoTime() - lastEnemyL > randomDelayL){
                spawnEnemyL();
            }
            // Mover los enemigos y eliminar los que estén fuera de la pantalla
            for (Enemy enemy : enemiesL) {
                enemy.move(5, 0);
                if (enemy.position.x > Gdx.graphics.getWidth() * 3) {
                    enemiesL.removeValue(enemy, true);
                }
            }
        }
    }

    public void updatePlayer(){

    }

    private void spawnBullet() {
        //crear la bala según la orientación del jugador
        if (playerDirection == 1) {
            Bullet newBullet = new Bullet(new Vector2(player.position.x + player.currentFrame.getRegionWidth(), player.position.y + 140),"BulletR");
            bulletsR.add(newBullet);
        } else {
            Bullet newBullet = new Bullet(new Vector2(player.position.x, player.position.y + 140),"BulletL");
            bulletsL.add(newBullet);
        }
    }

    public void updateBullets(){
        //mover las balas de la derecha
        for (Bullet bullet : bulletsR) {
            bullet.move(10, 0);
            if (bullet.position.x > player.position.x + player.currentFrame.getRegionWidth() +200){
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





    public void handleCollisions() {
        for (Enemy enemy : enemiesR) {
            //colision de un enemigo que viene por la derecha con el jugador
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    gameOver();
                }
                enemiesR.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    ResourceManager.getMp3Sound("hurt").play();
            }
            //colision de un enemigo que viene por la derecha con la bala
            for (Bullet bullet : bulletsR) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesR.removeValue(enemy, true);
                    bulletsR.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
            for (Bullet bullet : bulletsL) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesR.removeValue(enemy, true);
                    bulletsL.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
        }

        //colision de un enemigo que viene por la izquierda con el jugador
        for (Enemy enemy : enemiesL) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    if (PreferencesManager.isSoundEnable()){
                        ResourceManager.getWavSound("gameover").play();
                    }
                    gameOver();
                }
                enemiesL.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    ResourceManager.getMp3Sound("hurt").play();
            }
            //colision de un enemigo que viene por la izquierda con la bala
            for (Bullet bullet : bulletsL) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesL.removeValue(enemy, true);
                    bulletsL.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
            for (Bullet bullet : bulletsR) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesL.removeValue(enemy, true);
                    bulletsR.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
        }

        for (Enemy enemy : enemiesRTiled) {
            //colision de un enemigoTiled que viene por la derecha con el jugador
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    gameOver();
                }
                enemiesRTiled.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    ResourceManager.getMp3Sound("hurt").play();
            }
            //colision de un enemigoTiled que viene por la derecha con la bala
            for (Bullet bullet : bulletsR) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesRTiled.removeValue(enemy, true);
                    bulletsR.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
            for (Bullet bullet : bulletsL) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesRTiled.removeValue(enemy, true);
                    bulletsL.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
        }

        //colision de un enemigoTiled que viene por la izquierda con el jugador
        for (Enemy enemy : enemiesLTiled) {
            if (enemy.rect.overlaps(player.rect)) {
                player.lives--;
                if (player.lives == 0) {
                    pause = true;
                    if (PreferencesManager.isSoundEnable()){
                        ResourceManager.getWavSound("gameover").play();
                    }
                    gameOver();
                }
                enemiesLTiled.removeValue(enemy, true);
                if (PreferencesManager.isSoundEnable())
                    ResourceManager.getMp3Sound("hurt").play();
            }
            //colision de un enemigo que viene por la izquierda con la bala
            for (Bullet bullet : bulletsL) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesLTiled.removeValue(enemy, true);
                    bulletsL.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
            for (Bullet bullet : bulletsR) {
                if (enemy.rect.overlaps(bullet.rect)) {
                    score += 100;
                    enemiesLTiled.removeValue(enemy, true);
                    bulletsR.removeValue(bullet, true);
                    if (PreferencesManager.isSoundEnable())
                        ResourceManager.getWavSound("explosion").play();
                }
            }
        }
        // colisión con el muro
        for (Stone stone : stones){
            Rectangle stoneRect = new Rectangle(stone.getX(), stone.getY(), stone.getWidth(), stone.getHeigth());
            if (player.rect.overlaps(stoneRect) || isOnGround) {
                if (player.position.x < stone.getX()){
                    noMoveRigth = true;
                } else noMoveRigth = false;
                if (player.position.x > stone.getX()){
                    noMoveLeft = true;
                } else noMoveLeft = false;
            } else {
                noMoveLeft = false;
                noMoveRigth = false;
            }

        }
        //colisión con la meta
        Rectangle goalRect = new Rectangle(goal.getX(),goal.getY(), goal.getWidth(), goal.getHeigth());
        if (player.rect.overlaps(goalRect)){
            player.setName(playerNameText);
            player.setScore(score);
            pause= true;
            showVictoryMessage();
        }
    }

    public void update(float dt){
        if (!pause) {
            updateEnemies();
        }
        updateBullets();
        if (victoryMessageDisplayed) {
            elapsedTime += dt;
            if (elapsedTime >= victoryMessageDuration) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen2(player));
            }
        }
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
                if (PreferencesManager.isSoundEnable()){
                    ResourceManager.getWavSound("jump").play();
                }
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
                if (PreferencesManager.isSoundEnable()){
                    ResourceManager.getWavSound("gun").play();
                }
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
        bullet.dispose();
        enemiesL.clear();
        enemiesR.clear();
        enemiesLTiled.clear();
        enemiesRTiled.clear();
    }

    public void showVictoryMessage() {

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();

        // Crea la etiqueta del mensaje
        Label victoryLabel = new Label("¡Fase superada!", style);
        victoryLabel.setPosition(Gdx.graphics.getWidth() / 2 - victoryLabel.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        // Agrega la etiqueta
        stage.addActor(victoryLabel);
        victoryMessageDisplayed = true;


    }
    public static void gameOver(){
        GameOverMenuScreen gameOverScreen = new GameOverMenuScreen();
        gameOverScreen.setPlayerNameText(playerNameText);
        gameOverScreen.setScore(score);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(gameOverScreen);
            }
        }, 2);
    }


}
