package com.roshambo.models;

public class Players {
    private String username;
    private int password;
    private int wins;
    private int losses;
    private double winrate;

    public Players(String username, int password, int wins, int losses, double winrate) {
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
        this.winrate = winrate;
    }

    public Players() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getWinrate() {
        return winrate;
    }

    public void setWinrate(double winrate) {
        this.winrate = winrate;
    }

}
