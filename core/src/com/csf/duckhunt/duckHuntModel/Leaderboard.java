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
    private final Path pathToFile = Paths.get("core/assets/leaderboard.txt");
    static private Leaderboard instance;

    private Leaderboard() {

    }

    public void addRecord(Player player, int score) {
        try {
            Files.write(pathToFile, ("\n" + player.getName() + " " + score).getBytes(),
                    StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            System.err.println("Cannot open file: " + pathToFile);
        }
    }

    public List<String> getAllRecords() {
        List<String> records = new ArrayList<>();

        try {
            records = Files.readAllLines(pathToFile);
            records.sort((o1, o2)->{
                int sc1 = Integer.parseInt(o1.split(" ")[1]);
                int sc2 = Integer.parseInt(o2.split(" ")[1]);
                return sc2 - sc1;
            });
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
