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
import com.csf.duckhunt.duckHuntModel.Leaderboard;

import java.util.List;

/**
 * Created by vlad on 12/22/15.
 */
public class LeadersScreen implements Screen {
    SpriteBatch batch;
    Texture background;
    Sprite backSprite;
    Skin skin;
    Stage stage;

    private final int recordsPerPage = 10;
    private final int topOffset = 30;
    private final int leftOffset = 150;
    private final int offsetBtwRecords = 40;
    private final int maxDisplayedName = 20;
    private final BitmapFont font = new BitmapFont();
    private Game game;
    private List<String> leaderboardRecords = Leaderboard.getInstance().getAllRecords();

    public LeadersScreen() {
        this(null);
    }

    public LeadersScreen(Game game) {
        create();
        this.game = game;
        font.getData().setScale(2.5f);
    }

    private void create() {
        batch = new SpriteBatch();
        this.batch = new SpriteBatch();
        this.background = new Texture(Gdx.files.internal("core/assets/background.png"));
        this.backSprite = new Sprite(background);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createBasicSkin();

        final TextButton backButton = new TextButton("Back", skin);
        backButton.setPosition(Gdx.graphics.getWidth() / 2 - 100, 50);
        stage.addActor(backButton);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    private void createBasicSkin(){
        //Create a font\
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        backSprite.draw(batch);

        StringBuffer sb = new StringBuffer("Name");
        for (int j = 4; j <38; j++) {
            sb.append(" ");
        }
        sb.append("Score");
        font.setColor(Color.BLUE);
        font.draw(batch, sb.toString(), leftOffset, Gdx.graphics.getHeight() - topOffset);
        font.setColor(Color.WHITE);

        for (int i = 0; i < (recordsPerPage > leaderboardRecords.size() ? leaderboardRecords.size() : recordsPerPage); i++) {
            String[] recordSplitted = leaderboardRecords.get(i).split(" ");
            if (recordSplitted[0].length() > maxDisplayedName) {
                recordSplitted[0] = recordSplitted[0].substring(0, maxDisplayedName);
            }
            else {
                sb = new StringBuffer(recordSplitted[0]);
                for (int j = recordSplitted[0].length(); j < maxDisplayedName; j++) {
                    sb.append(" ");
                }
                recordSplitted[0] = sb.toString();
            }

            font.draw(batch, recordSplitted[0] + " " + recordSplitted[1],
                        leftOffset, Gdx.graphics.getHeight() - topOffset -  (i + 1) * offsetBtwRecords);
        }
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
