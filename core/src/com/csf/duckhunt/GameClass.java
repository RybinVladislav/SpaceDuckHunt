package com.csf.duckhunt;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.csf.duckhunt.UI.IntroScreen;
import com.csf.duckhunt.UI.MenuScreen;

public class GameClass extends Game {

	public MenuScreen menu;
	public SpriteBatch batch;
	public BitmapFont font;

	@Override
	public void create() {
		menu = new MenuScreen();
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new IntroScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();
	}
}
