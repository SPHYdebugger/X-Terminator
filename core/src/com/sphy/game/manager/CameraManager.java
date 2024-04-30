package com.sphy.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.sphy.game.utils.Constants.TILE_WIDTH;

public class CameraManager {

    SpriteManager spriteManager;
    LevelManager levelManager;
    OrthographicCamera camera;




    public CameraManager(SpriteManager spriteManager, LevelManager levelManager){
        this. spriteManager = spriteManager;
        this.levelManager = levelManager;


        camera =new OrthographicCamera();
        camera.setToOrtho(false,40*32,20*32);
        camera.update();


    }

    public void handleCamera(){
        if (spriteManager.player.position.x <(float) (40*TILE_WIDTH)/2){
            camera.position.set((float) (40*TILE_WIDTH)/2, (float)(20*TILE_WIDTH)/2,0);
        }else if  (spriteManager.player.position.x >(float) (125*TILE_WIDTH)/2){
                camera.position.set((float) (125*TILE_WIDTH)/2, (float)(20*TILE_WIDTH)/2,0);
        }else{
            camera.position.set(spriteManager.player.position.x,(float)(20*TILE_WIDTH)/2,0);
        }


        camera.update();
        levelManager.mapRender.setView(camera);
        levelManager.mapRender.render(new int[]{0,1,2,3});


    }

    public Vector2 getCameraPosition() {
        return new Vector2(camera.position.x, camera.position.y);
    }

    public float getViewportWidth() {
        return camera.viewportWidth;
    }



}
