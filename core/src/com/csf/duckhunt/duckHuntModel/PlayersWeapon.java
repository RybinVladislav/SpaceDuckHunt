package com.csf.duckhunt.duckHuntModel;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Δενθρ on 20.12.2015.
 */
public class PlayersWeapon {
    public final int maxShotsBeforeReload = 5;
    public final long reloadingTime = 2000;
    public final long timeBetweenShots = 500;
    public final int damage = 1;

    private Vector2 position;
    private boolean isReloading = false;
    private long reloadingStartTime;
    private int remainingShots = maxShotsBeforeReload;

    public PlayersWeapon(Vector2 position) {
        this.position = position;
    }

    public void Fire() {
        if (isReloading) {
            TryEndReloading();
        }

        if (isReloading) {
            return;
        }

        remainingShots--;

        if (remainingShots <= 0) {
            StartReloading();
        }
    }

    public int getRemainingShots() {
        return remainingShots;
    }

    public Vector2 getPosition() {
        return position;
    }

    private void StartReloading() {
        isReloading = true;
        reloadingStartTime = TimeManager.getInstance().getCurrentRoundTime();
    }

    private void TryEndReloading() {
        if (TimeManager.getInstance().getCurrentRoundTime() - reloadingStartTime >= reloadingTime) {
            remainingShots = maxShotsBeforeReload;
            isReloading = false;
        }
    }
}
