package com.csf.duckhunt.duckHuntModel;

/**
 * Created by Δενθρ on 21.12.2015.
 */
public class Player {
    static private Player instance;

    private String name = "unnamed";

    private Player() {

    }

    static public Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }

        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
