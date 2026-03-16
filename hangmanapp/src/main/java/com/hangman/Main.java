package com.hangman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangman.model.Player;
import java.lang.reflect.Type;

public class Main {
    public static String gChars;
    public static ArrayList<Player> players = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        System.out.println("""
                [1] Login
                [2] Signup
                """);
        int c = sc.nextInt();
        switch (c) {
            case 1:
                authenticate();
                break;
            case 2:
                signUp();
                break;
        }
    }

    public static void signUp() {
        sc.nextLine();
        while (true) {
            Player p = new Player();
            System.out.print("Enter your username: ");
            String name = sc.nextLine();
            System.out.print("Enter your 4-digit password: ");
            int password = sc.nextInt();
            p.setPlayerName(name);
            p.setPassword(password);
            p.setPlayerScore(0);
            players.add(p);
            break;
        }
        playGame();

    }

    public static void playGame() {
        System.out.println(players.size());
        int s = 0;
        String[] players = new String[50];
        int[] scores = new int[50];
        ;

        while (s <= 50) { // while loop to 50 allows 50 maximum amount of players
            int[] chances = { 10 }; // gives player 10 max guesses
            String randomWord = getRandomWord(); // calls the getRandomWord method and stores it in randomWord
            char[] chars = new char[randomWord.length()];
            for (int j = 0; j < randomWord.length(); j++) { // appends asterisks to mask words
                chars[j] = '*';
            }
            gChars = ""; // to track already guessed letters
            String completedWord = ""; // for comparison with the random word
            System.out.print("Player name: ");
            String name = sc.nextLine();
            System.out.println("Your word is: " + randomWord);
            for (int i = 1; i <= chances[0] && !isWordComplete(chars, randomWord); i++) {
                System.out.print("Enter a letter in word ********** > ");
                char userGuess = sc.next().charAt(0);
                sc.nextLine();
                boolean isCorrect = guessWord(randomWord, userGuess, chars, scores, s, chances);
                if (!isCorrect) {
                    chances[0] -= 1;
                }
                System.out.println(chars);
            }
            for (int i = 0; i < randomWord.length(); i++) { // builds the complete word from chars array
                completedWord += chars[i];
                if (randomWord.equals(completedWord)) {
                }
            }
            if (randomWord.equals(completedWord)) {
                System.out.println("Congratulations! You guessed the word correctly");
            } else {
                System.out.println("GAME OVER");
                System.out.println("The word is " + randomWord);
            }

            System.out.print("Another Player? Enter y or n: ");
            String response = sc.nextLine();
            players[s] = name;
            s++;
            if (response.contains("y")) { // continues after y
                continue;
            } else if (response.contains("n")) { // breaks the loop, then proceeds to leaderboard
                break;
            } else {
                System.out.print("Invalid response, y or n: ");
                sc.next();
            }

        }
        System.out.println("\n===== LEADERBOARD  =====");
        for (int i = 0; i < s - 1; i++) {
            for (int j = 0; j < s - i - 1; j++) { // bubble sorting sorts adjacent array members. This applies to both
                                                  // the scores and players array
                if (scores[j] < scores[j + 1]) {
                    int tempScore = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = tempScore;
                    String tempPlayer = players[j];
                    players[j] = players[j + 1];
                    players[j + 1] = tempPlayer;
                }
            }
        }
        for (int p = 0; p < s; p++) { // this loop prints the leaderboard
            System.out.println(scores[p] + " - " + players[p]);
        }
        sc.close();
    }

    public static void authenticate() {
        sc.nextLine();
        System.out.print("Enter name: ");
        String userName = sc.nextLine();
        System.out.print("Enter password: ");
        int password = sc.nextInt();
        for (Player p : players) {
            if (userName.equals(p.getPlayerName()) && password == p.getPassword()) {
                System.out.printf("""
                        Welcome back! %s
                        Latest score is: %d
                        """, p.getPlayerName(), p.getPlayerScore());
            }
        }
    }

    public static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/data.json"))) {
            Type playerListType = new TypeToken<ArrayList<Player>>() {
            }.getType();
            Gson gson = new Gson();
            players = gson.fromJson(br, playerListType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomWord() { // this method returns a random word
        String word = "";
        ArrayList<String> words = new ArrayList<>();
        try (Scanner sc = new Scanner("data/words.txt")) {
            while (sc.hasNextLine()) {
                words.add(sc.nextLine());
            }
            int randomNum = (int) (Math.random() * 49);
            word = words.get(randomNum);
        }
        return word;
    }

    public static boolean guessWord(String word, char user, char[] words, int[] scores, int playerIndex,
            int[] chances) {
        if (gChars.contains(user + "")) {
            System.out.println(user + " is already guessed.");
            return true;
        }

        boolean isCorrect = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == user) {
                words[i] = user;
                isCorrect = true;
                scores[playerIndex]++;
            }
        }

        if (isCorrect) {
            gChars += user;
        } else {
            System.out.println(user + " is not in the word");
            chances[0]--;
        }

        return isCorrect;
    }

    public static boolean isWordComplete(char[] words, String word) { // verification checker
        boolean complete = false;
        String buildWord = "";
        for (int i = 0; i < word.length(); i++) {
            buildWord += words[i];
        }
        if (buildWord.equals(word)) {
            complete = true;
        }
        return complete;
    }
}