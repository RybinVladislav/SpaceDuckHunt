package com.csf.duckhunt.duckHuntModel;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Δενθρ on 20.12.2015.
 */
public class Spaceship {
    public final int maxHealth = 3;

    private Vector2 position;
    private Vector2 moveDirection;
    private float moveSpeed;
    private Rectangle boundingBox;
    private int currentHealth;
    private DestroyableObjectState currentState = DestroyableObjectState.Alive;
    private int destructionScore;

    public Spaceship(Vector2 startingPosition, Vector2 startingMoveDirection, float startingMoveSpeed,
                     int destructionScore, float width, float height) {
        position = startingPosition;
        moveDirection = startingMoveDirection;
        this.destructionScore = destructionScore;
        moveSpeed = startingMoveSpeed;

        boundingBox.width = width;
        boundingBox.height = height;
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;

        if (currentHealth <= 0) {
            destroy();
        }
    }

    public void update() {
        move();
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getDestructionScore() {
        return destructionScore;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public DestroyableObjectState getCurrentState() {
        return currentState;
    }

    private void move() {
        position = position.add(moveDirection.scl(moveSpeed));
    }

    private void destroy() {
        currentState = DestroyableObjectState.Destroyed;
    }
}
