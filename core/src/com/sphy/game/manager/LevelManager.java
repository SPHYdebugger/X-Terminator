package com.sphy.game.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LevelManager {
    TiledMap map;
    TiledMapTileLayer backgroundLayer;
    TiledMapTileLayer collitionLayer;
    MapLayer objectsLayer;
    OrthogonalTiledMapRenderer mapRender;
    SpriteManager spriteManager;
    CameraManager cameraManager;
    public Batch batch;


    public LevelManager(SpriteManager spriteManager){
        this.spriteManager = spriteManager;

    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void loadCurrentLevel(){
        map = new TmxMapLoader().load("levels/Xterminator.tmx");
        collitionLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectsLayer = map.getLayers().get("objects");
        mapRender = new OrthogonalTiledMapRenderer(map);
        batch = mapRender.getBatch();


        //loadEnemies();

    }

    private void loadEnemies() {
    }

    public void restartCurrentLevel(){

    }



}
