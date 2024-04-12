package com.sphy.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {

    private static Preferences prefs = Gdx.app.getPreferences("GamePreferences");;

    public static boolean soundEnable(){
        return prefs.getBoolean("sound");
    }

}
