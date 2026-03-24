package com.hangmanwithclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hangmanwithclient.model.Player;

import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static String gChars;
    public static ArrayList<Player> players = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        onServer();
        // loadData();
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

    public static void onServer() {
        String randomWord = getRandomWord();
        int port = 8000;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Waiting for client to be connected...");
            Socket client = server.accept(); // locks in the client
            PrintWriter out = new PrintWriter(client.getOutputStream(), true); // sends out message to the client
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); // reads input from
                                                                                                    // the client

            System.out.println("Client is connected...");

            while (true) {
                // listen for client message
                System.out.println("Waiting for client message...");

                // read client's message
                String message = in.readLine();
                
                // determine if server's quitting
                if (message == null || message.equalsIgnoreCase("/quit")) {
                    System.out.println("Server disconnected...");
                    break;
                }

                // display client's message
                System.out.println("Client: " + message);

                // reply to client
                // type your message
                out.println("Guess a letter in: **********");
                String reply = in.readLine();
                // send your message
                // out.println(serverReply);

            }

            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUp() throws IOException {
        sc.nextLine();
        Player p = new Player();
        System.out.print("Enter your username: ");
        String name = sc.nextLine();
        System.out.print("Enter your 4-digit password: ");
        int password = sc.nextInt();
        p.setPlayerName(name);
        p.setPassword(password);
        p.setPlayerScore(0);
        players.add(p);
        playGame(p.getPlayerName());
    }

    public static String playGame(String name) throws IOException {
        // System.out.println("Player name: " + name);
        String response = "";
        String toBeSent = "";
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
            for (int i = 1; i <= chances[0] && !isWordComplete(chars, randomWord); i++) {
                boolean isAlreadyGuessed = false;
                System.out.println(randomWord);
                System.out.print("Enter a letter in word ************ > ");
                char userGuess = sc.next().charAt(0);
                sc.nextLine();
                boolean isCorrect = guessWord(randomWord, userGuess, chars, score, isAlreadyGuessed);
                if (isCorrect && !isAlreadyGuessed) {
                    System.out.println("Correct guess!");
                } else {
                    System.out.println("Incorrect guess!");
                    score[0]--;
                    if (score[0] < 0)
                        score[0] = 0;

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
                sc.close();
                break;
            }
            completedWord = toBeSent;
        } while (response.contains("y"));
        return toBeSent;
    }

    public static void leaderboard() {
        System.out.printf("%-9s %-1s \n", "NAME", "SCORE");
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(i).getPlayerScore() < players.get(j).getPlayerScore()) {
                    Player s;
                    s = players.get(j);
                    players.set(j, players.get(i));
                    players.set(i, s);
                }
            }
        }
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

    public static boolean guessWord(String word, char user, char[] words, int[] n, boolean isGuessed) {
        if (gChars.contains(user + "")) {
            System.out.println(user + " is already guessed.");
            return isGuessed;
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
        if (isCorrect && !isGuessed) {
            return true;
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