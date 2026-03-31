package com.hangmanpvp;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangmanpvp.Models.Player;
import com.hangmanpvp.Service.Game;

public class Server {
    public static ArrayList<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        int port = 8000;
        loadPlayers();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Waiting for players to be connected...");

            Socket client1 = server.accept();
            PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));

            System.out.println("User 1 is connected.");

            out1.println("user1");
            Socket client2 = server.accept();
            PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
            System.out.println("User 2 connected.");
            out2.println("user2");

            System.out.println("Both can now play!");
            out1.println("READY");
            out2.println("READY");
            String client1name = login(in1.readLine(), Integer.parseInt(in1.readLine()));
            while (true) {
                out1.println("Guess a letter in: " + currentWord());
                String player1 = in1.readLine();
            }

        } catch (IOException e) {

            System.out.println("Chat has ended...");
        }
    }

    public static String login(String name, int password) {
        boolean isFound = false;
        for (Player p : players) {
            if (name.equals(p.getPlayerName()) && password == p.getPassword()) {
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            System.out.println("Account not found...");
        }
        return name;
    }

    public static void signup(String name, int password) {
        Player p = new Player();
        p.setPlayerName(name);
        p.setPassword(password);
        p.setPlayerScore(0);
        players.add(p);
    }

    public static void loadPlayers() {
        try (FileReader fr = new FileReader("data/players.json")) {
            Type playersList = new TypeToken<ArrayList<Player>>() {
            }.getType();
            Gson gson = new Gson();
            players = gson.fromJson(fr, playersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String currentWord() {
        Game game = new Game();
        String word = game.getRandomWord();
        game.initializer(word);
        return word;
    }
}