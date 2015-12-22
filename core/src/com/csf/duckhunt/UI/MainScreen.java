package com.csf.duckhunt.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.csf.duckhunt.duckHuntModel.Battlefield;
import com.csf.duckhunt.duckHuntModel.Spaceship;


public class MainScreen implements Screen {

    final Game game;
    private SpriteBatch batch;
    private Texture background;
    private Sprite backSprite;
    private OrthographicCamera camera;
    private Texture ship;
    private Sprite shipSprite;

    private Battlefield battlefield = Battlefield.getInstance();

    public MainScreen(Game game) {
        this.game = game;
        create();
    }

    public void create() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 853, 640);
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("core/assets/background.png"));
        this.backSprite = new Sprite(background);
        this.ship = new Texture(Gdx.files.internal("core/assets/ship.png"));
        this.shipSprite = new Sprite(ship);
        Pixmap pm = new Pixmap(Gdx.files.internal("core/assets/crosshair.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        battlefield.start();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();

        backSprite.draw(batch);

        for (Spaceship spaceship: battlefield.spaceships) {
            shipSprite.setCenterX(spaceship.getPosition().x);
            shipSprite.setCenterY(spaceship.getPosition().y);
            System.out.println(spaceship.getPosition().x);
            shipSprite.draw(batch);
        }

        batch.end();

        battlefield.setAimPosition(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        battlefield.update();
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

    }
}
