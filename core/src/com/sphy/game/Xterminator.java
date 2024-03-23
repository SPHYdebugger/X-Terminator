package com.sphy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sphy.game.domain.Player;
import com.sphy.game.screen.MainMenuScreen;

public class Xterminator extends Game {


	@Override
	public void create () {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
