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
import com.sphy.game.domain.FinalEnemy;
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
    FinalEnemy finalEnemy;


    public LevelManager2(SpriteManager2 spriteManager2){
        this.spriteManager2 = spriteManager2;

    }

    public void setCameraManager(CameraManager2 cameraManager2) {
        this.cameraManager2 = cameraManager2;
    }

    public void loadCurrentLevel2(){
        map = new TmxMapLoader().load("levels/FinalLevel.tmx");
        collitionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectsLayer = map.getLayers().get("objects");
        mapRender = new OrthogonalTiledMapRenderer(map);
        batch = mapRender.getBatch();
        System.out.println("a punto de entrar2");
        int objectsNumber = objectsLayer.getObjects().getCount();
        System.out.println("numero de objetos en la capa2 " + objectsNumber);

        loadStones();

        loadFinalEnemy();
        //loadGoal();
    }

    private void loadFinalEnemy() {
        for (MapObject object : objectsLayer.getObjects()){

            if (object instanceof TiledMapTileMapObject){

                TiledMapTileMapObject objectTitle = (TiledMapTileMapObject) object;

                if (objectTitle.getProperties().containsKey("aranaFinal")){
                    finalEnemy = new FinalEnemy(new Vector2(objectTitle.getX(),objectTitle.getY()-150),"aranaFinal",10, 3);
                    System.out.println("ENEMIGO Final AÑADIDO");
                    spriteManager2.setEnemyTiled(finalEnemy);

                }

            }


        }
    }



    private void loadStones(){

        for (MapObject object : objectsLayer.getObjects()){

            if (object instanceof TiledMapTileMapObject){

                TiledMapTileMapObject objectTitle = (TiledMapTileMapObject) object;

                if (objectTitle.getProperties().containsKey("stone")){
                    System.out.println("plataforma AÑADIDA");
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



    public void restartCurrentLevel(){

    }



}
