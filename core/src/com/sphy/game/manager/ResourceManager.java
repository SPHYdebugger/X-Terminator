package com.sphy.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

import static com.sphy.game.utils.Constants.TEXTURE_ATLAS;

public class ResourceManager {


    private static AssetManager assetManager = new AssetManager();

    public static boolean update() {
        return assetManager.update();
    }

    public static void loadAllResources() {
        assetManager.load("textures/xter.atlas", TextureAtlas.class);
        loadAllSounds();
    }

    public static TextureRegion getTexture(String name) {
        return assetManager.get("textures/"+TEXTURE_ATLAS, TextureAtlas.class).findRegion(name);
    }

    public static Array<TextureAtlas.AtlasRegion> getAnimation(String name) {
        return assetManager.get("textures/"+TEXTURE_ATLAS, TextureAtlas.class).findRegions(name);
    }

    private static void loadAllSounds() {

        assetManager.load("sounds/" + "explosion.wav", Sound.class);
        assetManager.load("sounds/" + "gameover.wav", Sound.class);
        assetManager.load("sounds/" + "gun.wav", Sound.class);
        assetManager.load("sounds/" + "hurt.mp3", Sound.class);
        assetManager.load("sounds/" + "jump.wav", Sound.class);
        assetManager.load("sounds/" + "popdance.mp3", Sound.class);
        assetManager.load("sounds/" + "spider-attack.mp3", Sound.class);

    }

    public static Sound getWavSound(String name) {
        return assetManager.get("sounds/" + name + ".wav");
    }
    public static Sound getMp3Sound(String name) {
        return assetManager.get("sounds/" + name + ".mp3");
    }
}
