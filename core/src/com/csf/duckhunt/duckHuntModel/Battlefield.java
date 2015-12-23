package com.csf.duckhunt.duckHuntModel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by ����� on 20.12.2015.
 */
public class Battlefield {
    public final long spaceshipsCreationDuration = 1000;
    public final long roundTime = 60000;

    static private Battlefield instance;
    static public int initialSpaceshipsCount = 1;
    static public int width = 1000;
    static public int height = 1000;

    public Array<Spaceship> spaceships;
    private Array<Spaceship> destroyedSpaceships;
    private PlayersWeapon playersWeapon;
    private Vector2 aimPosition;
    private TimeManager timeManager = TimeManager.getInstance();
    private BattlefieldState currentState = BattlefieldState.stopped;
    private int currentScore = 0;
    private long lastSpaceshipCreationTime;
    private Rectangle standartSpaceshipBoundingBox;

    private Battlefield() {

    }

    public static Battlefield getInstance() {
        if (instance == null) {
            instance = new Battlefield();
        }

        return instance;
    }

    public void start() {
        if (currentState == BattlefieldState.stopped) {
            timeManager.start();
            initializeSpaceships();
            destroyedSpaceships = new Array<>();
            playersWeapon = new PlayersWeapon(new Vector2(width / 2, 0));
            currentState = BattlefieldState.active;
            aimPosition = new Vector2(-1000, -1000);
            currentScore = 0;
        }
    }

    public void pause() {
        if (currentState == BattlefieldState.active) {
            timeManager.pause();
            currentState = BattlefieldState.paused;
        }
    }

    public void unpause() {
        if (currentState == BattlefieldState.paused) {
            timeManager.unpause();
            currentState = BattlefieldState.active;
        }
    }

    public void stop() {
        currentState = BattlefieldState.stopped;
        spaceships = null;
        playersWeapon = null;
        timeManager.reset();
        Leaderboard.getInstance().addRecord(Player.getInstance(), currentScore);
    }

    public void update() {
        if (destroyedSpaceships.size > 0) {
            destroyedSpaceships.clear();
        }

        checkRoundTime();

        if (currentState != BattlefieldState.active) {
            return;
        }

        for (int i = 0; i < spaceships.size; i++) {
            Spaceship currentSpaceship = spaceships.get(i);
            currentSpaceship.update();

            if (new Rectangle(currentSpaceship.getPosition().x,
                                currentSpaceship.getPosition().y,
                                currentSpaceship.getBoundingBox().getWidth() / 2,
                                currentSpaceship.getBoundingBox().getHeight())
                    .overlaps(new Rectangle(aimPosition.x + 32, aimPosition.y, 32, 32)))
            {
                playersWeapon.Fire();
                currentSpaceship.takeDamage(playersWeapon.damage);

                if (currentSpaceship.getCurrentState() == DestroyableObjectState.Destroyed) {
                    handleSpaceshipDestruction(currentSpaceship);
                }

                checkSpaceshipLeftField(currentSpaceship);
            }
        }

        addNewSpaceship();
        aimPosition = new Vector2(-1000, -1000);
    }

    public void setAimPosition(Vector2 position) {
        aimPosition = position;
    }

    public Array<Spaceship> getSpaceships() {
        return spaceships;
    }

    public Array<Spaceship> getDestroyedSpaceships() {
        return destroyedSpaceships;
    }

    private void handleSpaceshipDestruction(Spaceship spaceship) {
        currentScore += spaceship.getDestructionScore();
        spaceships.removeValue(spaceship, true);
        destroyedSpaceships.add(spaceship);
    }

    private void checkSpaceshipLeftField(Spaceship spaceship) {
        if (spaceship.getPosition().x > width) {
            spaceships.removeValue(spaceship, true);
        }
    }

    private void addNewSpaceship() {
        if (timeManager.getCurrentRoundTime() - lastSpaceshipCreationTime > spaceshipsCreationDuration) {
            spaceships.add(getRandomSpaceship());
            lastSpaceshipCreationTime = timeManager.getCurrentRoundTime();
        }
    }

    private void initializeSpaceships() {
        spaceships = new Array<>();

        for (int i = 0; i < initialSpaceshipsCount; i++) {
            spaceships.add(getRandomSpaceship());
        }

        lastSpaceshipCreationTime = timeManager.getCurrentRoundTime();
    }

    private Spaceship getRandomSpaceship() {
        Vector2 position = new Vector2(0, MathUtils.random(64, 640-64));
        Vector2 direction = new Vector2(1, 1);
        return new Spaceship(position, direction, MathUtils.random(1.0f, 4.0f), 300, standartSpaceshipBoundingBox.getWidth(),
                standartSpaceshipBoundingBox.getHeight());
    }

    private void checkRoundTime() {
        if (timeManager.getCurrentRoundTime() > roundTime) {
            stop();
        }
    }

    public Rectangle getStandartSpaceshipBoundingBox() {
        return standartSpaceshipBoundingBox;
    }

    public void setStandartSpaceshipBoundingBox(Rectangle standartSpaceshipBoundingBox) {
        this.standartSpaceshipBoundingBox = standartSpaceshipBoundingBox;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public BattlefieldState getCurrentState() {
        return currentState;
    }
}
