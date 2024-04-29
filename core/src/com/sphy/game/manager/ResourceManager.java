package com.sphy.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.io.File;

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
        return assetManager.get("textures/xter.atlas", TextureAtlas.class).findRegion(name);
    }

    public static Array<TextureAtlas.AtlasRegion> getAnimation(String name) {
        return assetManager.get("textures/xter.atlas", TextureAtlas.class).findRegions(name);
    }

    private static void loadAllSounds() {
        assetManager.load("sounds" + File.separator + "explosion.wav", Sound.class);
    }

    public static Sound getSound(String name) {
        return assetManager.get("sounds/" + name + ".wav");
    }
}
