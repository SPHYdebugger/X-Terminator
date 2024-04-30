package com.sphy.game.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sphy.game.domain.Enemy;
import com.sphy.game.items.Goal;
import com.sphy.game.items.Stone;

public class LevelManager {
    TiledMap map;
    TiledMapTileLayer backgroundLayer;
    TiledMapTileLayer collitionLayer;
    MapLayer objectsLayer;
    OrthogonalTiledMapRenderer mapRender;
    SpriteManager spriteManager;
    CameraManager cameraManager;
    public Batch batch;
    Enemy enemy;


    public LevelManager(SpriteManager spriteManager){
        this.spriteManager = spriteManager;

    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void loadCurrentLevel(){
        map = new TmxMapLoader().load("levels/Xterminator.tmx");
        collitionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectsLayer = map.getLayers().get("Objects");
        mapRender = new OrthogonalTiledMapRenderer(map);
        batch = mapRender.getBatch();
        System.out.println("a punto de entrar1");
        int objectsNumber = objectsLayer.getObjects().getCount();
        System.out.println("numero de objetos en la capa1 " + objectsNumber);

        loadStones();

        loadEnemies();
        loadGoal();
    }

    private void loadEnemies() {
        for (MapObject object : objectsLayer.getObjects()){

            if (object instanceof TiledMapTileMapObject){

                TiledMapTileMapObject objectTitle = (TiledMapTileMapObject) object;

                if (objectTitle.getProperties().containsKey("enemyL")){
                    enemy = new Enemy(new Vector2(objectTitle.getX(),objectTitle.getY()),"arana");
                    System.out.println("ENEMIGO L AÑADIDO");
                    spriteManager.enemiesLTiled.add(enemy);

                }
                if (objectTitle.getProperties().containsKey("enemyR")){
                    enemy = new Enemy(new Vector2(objectTitle.getX(),objectTitle.getY()),"arana");
                    System.out.println("ENEMIGO R AÑADIDO");
                    spriteManager.enemiesRTiled.add(enemy);

                }
            }


        }
    }



    private void loadStones(){

        for (MapObject object : objectsLayer.getObjects()){

            if (object instanceof TiledMapTileMapObject){

                TiledMapTileMapObject objectTitle = (TiledMapTileMapObject) object;

                if (objectTitle.getProperties().containsKey("blocked")){
                    System.out.println("PIEDRA AÑADIDA");
                    float stoneX = objectTitle.getX();
                    float stoneY = objectTitle.getY();
                    float width = objectTitle.getProperties().get("width", Float.class);
                    float height = objectTitle.getProperties().get("height", Float.class);
                    Stone stone = new Stone(stoneX, stoneY, width,height);
                    spriteManager.stones.add(stone);

                }
            }
        }
    }

    private void loadGoal(){

        for (MapObject object : objectsLayer.getObjects()){

            if (object instanceof TiledMapTileMapObject){

                TiledMapTileMapObject objectTitle = (TiledMapTileMapObject) object;

                if (objectTitle.getProperties().containsKey("goal")){
                    System.out.println("meta AÑADIDA");
                    Goal goal = new Goal(objectTitle.getX(),objectTitle.getY(),objectTitle.getProperties().get("width", Float.class),objectTitle.getProperties().get("height", Float.class));
                    spriteManager.setGoal(goal);
                }
            }
        }
    }

    public void restartCurrentLevel(){

    }



}
