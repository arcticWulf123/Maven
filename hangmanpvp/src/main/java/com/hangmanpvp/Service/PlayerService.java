package com.hangmanpvp.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hangmanpvp.Models.Player;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlayerService {
    private int score;
    private ArrayList<Player>  players;

    public PlayerService() {
        loadPlayers();
    }
    public boolean login(String username, int password) {
        for (Player p : players) {
            if (p.getPlayerName().equals(username) &&
                    p.getPassword() == password) {
                return true;
            }
        }
        return false;
    }

    public void signUp (String username, int password) {
        Player p  = new Player();
        p.setPlayerName(username);
        p.setPassword(password);
        p.setPlayerScore(0);
        players.add(p);
    }

    public void loadPlayers() {
        try (FileReader fr = new FileReader("data/players.json")) {
            Type playersList = new TypeToken<ArrayList<Player>>() {
            }.getType();
            Gson gson = new Gson();
            players = gson.fromJson(fr, playersList);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void showLeaderBoard() {
        System.out.printf("%10s %s", "Player name", "Score \n");
        for (Player p : players) {
            System.out.println(p.toString());
        }
    }

    public void updateScore(String username, int score) {
        for (Player p : players) {
            if (p.getPlayerName().equals(username)) {
                p.setPlayerScore(score);
                break;
            }
        }
    }

    public void saveData() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("data/players.json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(players, bw);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
