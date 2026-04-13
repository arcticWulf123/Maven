package com.hangmanpvp;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangmanpvp.Models.Player;
import com.hangmanpvp.Service.GameService;
import com.hangmanpvp.Service.PlayerService;

public class Server {
    public static ArrayList<Player> players = new ArrayList<>();
    public static PlayerService playerService = new PlayerService();
    public static GameService gameService = new GameService();
    public static void main(String[] args) {
        int port = 8000;
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Waiting for players to be connected...");

            Socket client1 = server.accept();
            PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));

            System.out.println("User 1 is connected.");

            out1.println("Welcome to Hangman Game!");
            String clientName = "";
            while (true) {
                String client = in1.readLine();
                switch (client) {
                    case "1":
                        clientName = in1.readLine();
                        int password = Integer.parseInt(in1.readLine());

                        boolean success = playerService.login(clientName, password);

                        if (!success) {
                            out1.println("Invalid username or password");
                            continue;
                        } else {
                            out1.println("Logged in!");
                        }
                        break;
                    case "2":
                        clientName = in1.readLine();
                        password = Integer.parseInt(in1.readLine());
                        playerService.signUp(clientName,password);
                        continue;
                    case "3":
                        playerService.showLeaderBoard();
                        break;
                }
                break;
            }
            int currentScore = 10;

            String word = gameService.getRandomWord();
            out1.println(gameService.getCurrentState());

            while (!gameService.isGameOver() && currentScore > 0) {
                String userGuess = in1.readLine();
                char guess = userGuess.charAt(0);

                if (gameService.isAlreadyGuessed(guess)) {
                    currentScore--;
                    out1.println("Letter already guessed!");
                } else {
                    boolean correct = gameService.guess(guess);
                    if (correct) {
                        out1.println("Correct!");
                    } else {
                        currentScore--;
                        out1.println("Incorrect guess!");
                    }
                }

                if (gameService.isGameOver() || currentScore <= 0) {
                    out1.printf("Game over! Final score: %d \n", currentScore);
                } else {
                    out1.println(gameService.getCurrentState());
                }
            }
            out1.printf("Game over! Final score: %d \n", currentScore);
            playerService.updateScore(clientName, currentScore);
            playerService.showLeaderBoard();
            playerService.saveData();
        } catch (IOException e) {

            System.out.println("Chat has ended...");
        }
    }
}