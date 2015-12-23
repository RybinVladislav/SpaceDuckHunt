package com.csf.duckhunt.duckHuntModel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ����� on 20.12.2015.
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
    private float yOffset;
    private float startPosition;

    public Spaceship(Vector2 startingPosition, Vector2 startingMoveDirection, float startingMoveSpeed,
                     int destructionScore, float width, float height) {
        this.position = startingPosition;
        this.moveDirection = startingMoveDirection;
        this.destructionScore = destructionScore;
        this.moveSpeed = startingMoveSpeed;
        this.boundingBox = new Rectangle();
        this.boundingBox.setWidth(width);
        this.boundingBox.setHeight(height);
        this.yOffset = 0;
        this.startPosition = position.y;
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
        position = position.set(position.x  +moveDirection.x * moveSpeed, position.y + moveDirection.y * moveSpeed);

        yOffset = position.y - startPosition;
        if (yOffset > 40) {
            moveDirection.y = -1;
        } else if (yOffset < -40) {
            moveDirection.y = 1;
        }
    }

    private void destroy() {
        currentState = DestroyableObjectState.Destroyed;
    }
}
