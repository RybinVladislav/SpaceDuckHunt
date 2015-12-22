package com.csf.duckhunt.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainScreen implements Screen {
    SpriteBatch batch;
    Stage stage;
    Skin skin;

    private Game game;

    public MainScreen(Game game) {
        create();
        this.game = game;
    }

    public MainScreen() {
        create();
    }

    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(new Viewport() {
        }width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
