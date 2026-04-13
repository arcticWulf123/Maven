package com.hangmanpvp.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameService {
    private StringBuilder sb;
    private ArrayList<Character> gChars;
    private String word;
    private int currentScore;
    public String getRandomWord() {
        ArrayList<String> words = new ArrayList<>();
        try (Scanner reader = new Scanner(new File("data/words.txt"))) {
            while (reader.hasNextLine()) {
                words.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int randomNum = (int) (Math.random() * words.size());
        word = words.get(randomNum);

        initializeState();
        return getCurrentState();
    }

    private void initializeState() {
        sb = new StringBuilder();
        gChars = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            sb.append("*");
        }
    }

    public boolean guess(char user) {
        boolean found = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == user) {
                sb.setCharAt(i, user);
                gChars.add(user);
                found = true;
            }
        }

        return found;
    }

    public String getCurrentState() {
        return sb.toString();
    }

    public boolean isGameOver() {
        return sb.toString().equals(word);
    }
    public String getWord() {
        return word;
    }
    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public boolean isAlreadyGuessed(char user) {
        return gChars.contains(user);
    }

}
