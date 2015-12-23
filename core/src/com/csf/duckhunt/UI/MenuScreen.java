package com.csf.duckhunt.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {
    SpriteBatch batch;
    Texture background;
    Sprite backSprite;
    Skin skin;
    Stage stage;
    private ParticleEffect effect;

    private Game game;

    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture

        Texture button = new Texture(Gdx.files.internal("core/assets/button.png"));
        skin.add("background", button);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

    public MenuScreen(Game game) {
        create();
        this.game = game;
    }

    public MenuScreen() {
        create();
    }

    public void create() {
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("core/assets/background.png"));
        this.backSprite = new Sprite(background);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        createBasicSkin();

        final TextButton startGameButton = new TextButton("Start game", skin); // Use the initialized skin
        startGameButton.setPosition(Gdx.graphics.getWidth()/2-100 , 420);
        stage.addActor(startGameButton);

        final TextButton leadersButton = new TextButton("Leaderboards", skin); // Use the initialized skin
        leadersButton.setPosition(Gdx.graphics.getWidth()/2-100 , 280);
        stage.addActor(leadersButton);

        TextButton exitGameButton = new TextButton("Exit game", skin); // Use the initialized skin
        exitGameButton.setPosition(Gdx.graphics.getWidth()/2-100 , 140);
        stage.addActor(exitGameButton);

        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().dispose();
                game.setScreen(new MainScreen(game));
            }
        });

        leadersButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().dispose();
                game.setScreen(new LeadersScreen(game));
            }
        });

        exitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
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
        batch.end();
        stage.act();
        stage.draw();
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
        stage.dispose();
        skin.dispose();
    }
}
