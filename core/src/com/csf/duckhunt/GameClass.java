package com.csf.duckhunt;

import com.badlogic.gdx.Game;
import com.csf.duckhunt.UI.MainScreen;

public class GameClass extends Game {

	@Override
	public void create () {
		setScreen(new MainScreen(this));
	}
}
