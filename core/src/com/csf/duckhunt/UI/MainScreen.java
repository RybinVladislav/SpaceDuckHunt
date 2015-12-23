package com.csf.duckhunt.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.csf.duckhunt.duckHuntModel.Battlefield;
import com.csf.duckhunt.duckHuntModel.Spaceship;
import com.csf.duckhunt.duckHuntModel.TimeManager;


public class MainScreen implements Screen {

    private final Game game;
    private final int leftScoreOffset = 40;
    private final int topScoreOffset = 40;
    private final int leftTimePosition = Gdx.graphics.getWidth() / 2 - 30;
    private final int topTimePosition = Gdx.graphics.getHeight() - 30;
    private final BitmapFont font = new BitmapFont();

    private SpriteBatch batch;
    private Texture background;
    private Sprite backSprite;
    private OrthographicCamera camera;
    private Texture ship;
    private Sprite shipSprite;

    private Battlefield battlefield = Battlefield.getInstance();

    public MainScreen(Game game) {
        this.game = game;
        font.getData().setScale(2.5f);
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
        battlefield.setStandartSpaceshipBoundingBox(new Rectangle(0, 0,
                this.ship.getWidth(), this.ship.getHeight()));
        Battlefield.width = Gdx.graphics.getWidth();
        Battlefield.height = Gdx.graphics.getHeight();
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
            shipSprite.draw(batch);
        }

        font.draw(batch, "Score " + battlefield.getCurrentScore(), leftScoreOffset, topScoreOffset);
        long timeMin = (battlefield.roundTime - TimeManager.getInstance().getCurrentRoundTime()) / 1000 / 60;
        long timeSec = (battlefield.roundTime - TimeManager.getInstance().getCurrentRoundTime()) / 1000 % 60;
        font.draw(batch, timeMin + ":" + (timeSec > 9 ? timeSec : "0" + timeSec), leftTimePosition, topTimePosition);

        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(vec);
            battlefield.setAimPosition(new Vector2(vec.x, vec.y));
        }
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
