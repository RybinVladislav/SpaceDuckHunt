package com.csf.duckhunt.duckHuntModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Δενθρ on 21.12.2015.
 */
public class Leaderboard {
    private final Path pathToFile = Paths.get("src/leaderboard.txt");
    static private Leaderboard instance;

    private Leaderboard() {

    }

    public void addRecord(Player player, int score) {
        try {
            Files.write(pathToFile, (player.getName() + " " + score).getBytes(),
                    StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.err.println("Cannot open file: " + pathToFile);
        }
    }

    public List<String> getAllRecords() {
        List<String> records = new ArrayList<>();

        try {
            Files.readAllLines(pathToFile);
        }
        catch (IOException e) {
            System.err.println("Cannot open file: " + pathToFile);
        }

        return records;
    }

    static public Leaderboard getInstance() {
        if (instance == null) {
            instance = new Leaderboard();
        }

        return instance;
    }
}
