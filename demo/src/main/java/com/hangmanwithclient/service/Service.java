package com.hangmanwithclient.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {

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

    // public boolean isCorrect (char user) {

    // }

    
}
