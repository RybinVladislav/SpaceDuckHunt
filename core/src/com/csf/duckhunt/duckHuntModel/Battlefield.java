package com.csf.duckhunt.duckHuntModel;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Δενθρ on 20.12.2015.
 */
public class Battlefield {
    public final long spaceshipsCreationDuration = 4000;
    public final long roundTime = 60000;

    static private Battlefield instance;
    static public int initialSpaceshipsCount = 1;
    static public int width = 1000;
    static public int height = 1000;

    private Array<Spaceship> spaceships;
    private Array<Spaceship> destroyedSpaceships;
    private PlayersWeapon playersWeapon;
    private Vector2 aimPosition;
    private TimeManager timeManager = TimeManager.getInstance();
    private BattlefieldState currentState = BattlefieldState.stopped;
    private int currentScore = 0;
    private long lastSpaceshipCreationTime;

    private Battlefield() {

    }

    static public Battlefield getInstance() {
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

            if (currentSpaceship.getBoundingBox()
                    .overlaps(new Rectangle(aimPosition.x, aimPosition.y, 0, 0)))
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
    }

    public void setAimPosition(Vector2 position) {
        aimPosition = position;
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
        Vector2 position = new Vector2(0, height / 2);
        Vector2 direction = new Vector2(1, 0);
        return new Spaceship(position, direction, 1.0f, 300, 2.0f, 2.0f);
    }

    private void checkRoundTime() {
        if (timeManager.getCurrentRoundTime() > roundTime) {
            stop();
        }
    }
}
