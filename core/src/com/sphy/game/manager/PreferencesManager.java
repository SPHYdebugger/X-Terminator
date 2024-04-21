package com.sphy.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {

    private static Preferences prefs = Gdx.app.getPreferences("GamePreferences");;

    //configuraciones de sonido
    public static boolean existSound(){
        return prefs.contains("sound");
    }
    public static boolean isSoundEnable(){
        return prefs.getBoolean("sound");
    }
    public static void enableSound(){
        prefs.putBoolean("sound", true);
    }
    public static void disableSound(){
        prefs.putBoolean("sound", false);
    }
    public  static void switchSound(boolean value){
        prefs.putBoolean("sound", value);
    }
    public static void save(){
        prefs.flush();
    }

    //configuraciones de dificultad
    public static String getDifficulty(){
        return prefs.getString("difficulty");
    }
    public static void setDifficulty(String value){
        prefs.putString("difficulty", value);
    }

}
