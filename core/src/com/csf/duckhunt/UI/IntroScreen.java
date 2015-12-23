package com.csf.duckhunt.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IntroScreen implements Screen {

    final Game game;
    private SpriteBatch batch;
    private Texture background;
    private Sprite backSprite;
    private Texture title;
    private Sprite titleSprite;
    private float titleHeight;
    private int direction;


    public IntroScreen(final Game game) {
        this.game = game;
        this.create();
    }

    public void create() {
        this.titleHeight = 300;
        this.direction = 1;
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("core/assets/background.png"));
        this.backSprite = new Sprite(background);
        this.title = new Texture(Gdx.files.internal("core/assets/title.png"));
        this.titleSprite = new Sprite(title);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        // Drawing goes here!
        backSprite.draw(batch);
        if (this.titleHeight > 325) {
            direction = -1;
        }
        else if (this.titleHeight < 275) {
            direction = 1;
        }
        this.titleHeight += direction * (float)(25.0f * delta);
        titleSprite.setPosition(-25, this.titleHeight);
        titleSprite.setRotation(0);
        titleSprite.draw(batch);
        batch.end();
        if (Gdx.input.isTouched()) {
            game.getScreen().dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        background.dispose();
        title.dispose();
    }
}
