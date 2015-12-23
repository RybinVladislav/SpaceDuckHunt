package com.csf.duckhunt.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

/**
 * Created by Δενθρ on 23.12.2015.
 */
public class NameInputScreen implements Screen {
    SpriteBatch batch;
    Texture background;
    Sprite backSprite;
    Skin skin;
    Stage stage;

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

        TextArea.TextFieldStyle textFieldStyle = new TextArea.TextFieldStyle();
        textFieldStyle.background = skin.newDrawable("background");
        textFieldStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);
        skin.add("defaultText", textFieldStyle);
    }

    public NameInputScreen(Game game) {
        create();
        this.game = game;
    }

    public NameInputScreen() {
        create();
    }

    public void create() {
        batch = new SpriteBatch();
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("core/assets/background.png"));
        this.backSprite = new Sprite(background);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        createBasicSkin();

        TextArea userNameTextField = new TextArea("", skin, "defaultText");
        userNameTextField.setPosition(Gdx.graphics.getWidth()/2-100 , 300);
        stage.addActor(userNameTextField);

        TextButton exitGameButton = new TextButton("Ok", skin); // Use the initialized skin
        exitGameButton.setPosition(Gdx.graphics.getWidth()/2-100 , 140);
        stage.addActor(exitGameButton);

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

    }
}
