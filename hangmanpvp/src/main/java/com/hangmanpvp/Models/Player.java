package com.hangmanpvp.Models;

public class Player {
    private String playerName;
    private int password;
    private int playerScore;

    public Player(String playerName, int password, int playerScore) {
        this.playerName = playerName;
        this.password = password;
        this.playerScore = playerScore;
    }

    public Player () {}

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public String toString() {
        return String.format("%-11s %d", getPlayerName(), getPlayerScore());
    }
}