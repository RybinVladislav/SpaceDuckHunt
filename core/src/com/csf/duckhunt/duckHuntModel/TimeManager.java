package com.csf.duckhunt.duckHuntModel;

import java.util.Date;

/**
 * Created by Δενθρ on 21.12.2015.
 */
public class TimeManager {
    static private TimeManager instance;
    private long roundStartTime;
    private long pauseStartTime;
    private long sumPausesTime;
    private boolean isPaused = true;

    private TimeManager() {

    }

    public long getCurrentRoundTime() {
        if (isPaused) {
            return pauseStartTime - roundStartTime - sumPausesTime;
        }
        else {
            return new Date().getTime() - roundStartTime - sumPausesTime;
        }
    }

    public void start() {
        roundStartTime = new Date().getTime();
        isPaused = false;
    }

    public void pause() {
        if (!isPaused) {
            pauseStartTime = new Date().getTime();
            isPaused = true;
        }
    }

    public void unpause() {
        if (isPaused) {
            sumPausesTime += new Date().getTime() - pauseStartTime;
            isPaused = false;
        }
    }

    public void reset() {
        isPaused = true;
        roundStartTime = 0;
        pauseStartTime = 0;
        sumPausesTime = 0;
    }

    static public TimeManager getInstance() {
        if (instance == null) {
            instance = new TimeManager();
        }

        return instance;
    }
}
