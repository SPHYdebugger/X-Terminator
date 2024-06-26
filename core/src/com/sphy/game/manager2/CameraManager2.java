package com.sphy.game.manager2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import static com.sphy.game.utils.Constants.TILE_WIDTH;

public class CameraManager2 {

    SpriteManager2 spriteManager2;
    LevelManager2 levelManager2;
    OrthographicCamera camera;




    public CameraManager2(SpriteManager2 spriteManager2, LevelManager2 levelManager2){
        this. spriteManager2 = spriteManager2;
        this.levelManager2 = levelManager2;


        camera =new OrthographicCamera();
        camera.setToOrtho(false,40*32,20*32);
        camera.update();


    }

    public void handleCamera(){
        if (spriteManager2.player.position.x <(float) (40*TILE_WIDTH)/2){
            camera.position.set((float) (40*TILE_WIDTH)/2, (float)(19*TILE_WIDTH)/2,0);
        }else{
            camera.position.set(spriteManager2.player.position.x,(float)(19*TILE_WIDTH)/2,0);
        }

        camera.update();
        levelManager2.mapRender.setView(camera);
        levelManager2.mapRender.render(new int[]{0,1,2,3});


    }

    public Vector2 getCameraPosition() {
        return new Vector2(camera.position.x, camera.position.y);
    }

    public float getViewportWidth() {
        return camera.viewportWidth;
    }



}
