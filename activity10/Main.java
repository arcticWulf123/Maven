package com.hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hangman.model.Player;
import java.lang.reflect.Type;

public class Main {
    public static String gChars;
    public static ArrayList<Player> players = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        loadData();
        System.out.println("""
                [1] Login
                [2] Signup
                [3] Leaderboard
                """);
        int c = sc.nextInt();
        switch (c) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 3:
                leaderboard();
                break;
        }
    }

    public static void signUp() throws IOException {
        sc.nextLine();
        Player p = new Player();
        while (true) {
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
        playGame(p.getPlayerName());
    }

    public static void playGame(String name) throws IOException {
        System.out.println("Player name: " + name);
        String response = "";
        do { // while loop continues if user inputs y
            int[] chances = { 10 }; // gives player 10 max guesses
            String randomWord = getRandomWord(); // calls the getRandomWord method and stores it in randomWord
            char[] chars = new char[randomWord.length()];
            int[] score = { 0 };
            for (int j = 0; j < randomWord.length(); j++) { // appends asterisks to mask words
                chars[j] = '*';
            }
            gChars = ""; // to track already guessed letters
            String completedWord = ""; // for comparison with the random word
            System.out.println("Your word is: " + randomWord);
            for (int i = 1; i <= chances[0] && !isWordComplete(chars, randomWord); i++) {
                System.out.print("Enter a letter in word ********** > ");
                char userGuess = sc.next().charAt(0);
                sc.nextLine();
                boolean isCorrect = guessWord(randomWord, userGuess, chars, score);
                if (isCorrect) {
                    System.out.println("Correct guess!");
                } else {
                    System.out.println("Incorrect guess!");
                    if (score[0] == 0)
                        score[0] += 0;
                    score[0]--;
                }
                System.out.println(chars);
            }
            for (int i = 0; i < randomWord.length(); i++) { // builds the complete word from chars array
                completedWord += chars[i];
            }
            if (randomWord.equals(completedWord)) {
                System.out.printf("Congratulations! You win! Your score is: %d \n", score[0]);
                System.out.print("Keep current score? (y/n): ");
                String r = sc.nextLine();
                if (r.contains("y")) {
                    for (Player p : players) {
                        if (p.getPlayerName().equals(name)) {
                            p.setPlayerScore(score[0]);
                            break;
                        }
                    }
                    serializeToJson(players);
                    leaderboard();
                } else {
                    serializeToJson(players);
                    leaderboard();
                }
            } else {
                System.out.println("GAME OVER");
                System.out.println("The word is " + randomWord);
            }

            System.out.print("Play again? (y/n): ");
            response = sc.nextLine();
            if (response.contains("y")) { // continues after y
                continue;
            } else if (response.contains("n")) { // breaks the loop, then proceeds to leaderboard
                break;
            } else {
                System.out.print("Invalid response, y or n: ");
                sc.next();
            }
            sc.close();
        } while (response.contains("y"));
    }

    public static void leaderboard() {
        System.out.printf("%-9s %-1s \n", "NAME", "SCORE");
        players.sort((p1, p2) -> Integer.compare(p2.getPlayerScore(), p1.getPlayerScore()));
        for (Player p : players) {
            System.out.printf("%-9s %-1d \n", p.getPlayerName(), p.getPlayerScore());
        }

    }

    public static void serializeToJson(ArrayList<Player> p) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fw = new FileWriter("data/data.json");
        gson.toJson(p, fw);
        fw.close();
    }

    public static void login() throws IOException {
        sc.nextLine();
        boolean isFound = false;
        System.out.print("Enter name: ");
        String userName = sc.nextLine();
        System.out.print("Enter password: ");
        int password = sc.nextInt();
        sc.nextLine();
        for (Player p : players) {
            if (userName.equals(p.getPlayerName()) && password == p.getPassword()) {
                System.out.printf("""
                        Welcome back! %s
                        Latest score is: %d
                        """, p.getPlayerName(), p.getPlayerScore());
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Sorry... account not found");
            return;
        }
        System.out.print("Play a game? (y/n): ");
        String response = sc.nextLine();
        if (response.contains("y")) {
            playGame(userName);
        } else {
            System.out.println("Bye!");
        }
        System.out.println("Keep current score? (y/n): ");
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

    public static boolean guessWord(String word, char user, char[] words, int[] n) {
        if (gChars.contains(user + "")) {
            System.out.println(user + " is already guessed.");
            return true;
        }
        int matchCount = 0;
        boolean isCorrect = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == user) {
                words[i] = user;
                matchCount++;
                isCorrect = true;
            }
        }
        n[0] += matchCount;

        if (isCorrect) {
            gChars += user;
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