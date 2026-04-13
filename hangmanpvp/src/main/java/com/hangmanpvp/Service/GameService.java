package com.hangmanpvp.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
Contains
1. Account system
2. Guess checkers
3. Random word handler
4. 


*/
public class Game {
    private static StringBuilder sb = new StringBuilder();
    private static ArrayList<Character> gChars = new ArrayList<>();

    public String getRandomWord() { // this method returns a random word
        String word = "";
        ArrayList<String> words = new ArrayList<>();
        try (Scanner reader = new Scanner(new File("data/words.txt"))) {
            while (reader.hasNextLine()) {
                words.add(reader.nextLine());
            }
            int randomNum = (int) (Math.random() * words.size());
            word = words.get(randomNum);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return word;
    }

    public boolean guess(String word, char user) {
        if (gChars.contains(user)) {
            System.out.println("Already guessed!");
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == user) {
                sb.setCharAt(i, user);
                gChars.add(user);
            } else {
                System.out.println("Incorrect guess!");
                return false;
            }
        }

        return true;
    }

    public void currentState() { // reveals the current state of the string
        System.out.println(sb.toString());
    }

    public void initializer(String word) {
        for (int i = 0; i < word.length(); i++) {
            sb.append("*");
        }
    }
}