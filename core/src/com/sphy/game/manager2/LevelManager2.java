package com.sphy.game.manager2;

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


public class LevelManager2 {
    TiledMap map;
    TiledMapTileLayer backgroundLayer;
    TiledMapTileLayer collitionLayer;
    MapLayer objectsLayer;
    OrthogonalTiledMapRenderer mapRender;
    SpriteManager2 spriteManager2;
    CameraManager2 cameraManager2;
    public Batch batch;
    Enemy enemy;


    public LevelManager2(SpriteManager2 spriteManager2){
        this.spriteManager2 = spriteManager2;

    }

    public void setCameraManager(CameraManager2 cameraManager2) {
        this.cameraManager2 = cameraManager2;
    }

    public void loadCurrentLevel2(){
        map = new TmxMapLoader().load("levels/Xterminator.tmx");
        collitionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectsLayer = map.getLayers().get("Objects");
        mapRender = new OrthogonalTiledMapRenderer(map);
        batch = mapRender.getBatch();
        System.out.println("a punto de entrar");
        //int objectsNumber = objectsLayer.getObjects().getCount();
        //System.out.println("numero de objetos en la capa " + objectsNumber);

        //loadStones();

        //loadEnemies();
        //loadGoal();
    }

    private void loadEnemies() {
        for (MapObject object : objectsLayer.getObjects()){

            if (object instanceof TiledMapTileMapObject){

                TiledMapTileMapObject objectTitle = (TiledMapTileMapObject) object;

                if (objectTitle.getProperties().containsKey("enemyL")){
                    enemy = new Enemy(new Vector2(objectTitle.getX(),objectTitle.getY()),"arana");
                    System.out.println("ENEMIGO L AÑADIDO");
                    spriteManager2.enemiesLTiled.add(enemy);

                }
                if (objectTitle.getProperties().containsKey("enemyR")){
                    enemy = new Enemy(new Vector2(objectTitle.getX(),objectTitle.getY()),"arana");
                    System.out.println("ENEMIGO R AÑADIDO");
                    spriteManager2.enemiesRTiled.add(enemy);

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
                    spriteManager2.stones.add(stone);

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
                    spriteManager2.setGoal(goal);
                }
            }
        }
    }

    public void restartCurrentLevel(){

    }



}
