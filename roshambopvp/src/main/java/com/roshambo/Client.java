package com.roshambo;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.roshambo.models.Players;

public class Client {
    public static ArrayList<Players> players = new ArrayList<>();
    public static Scanner sc = new Scanner(System.in);
    public static int c1Score, c2Score;
    public static String user1Name, user2Name;

    public static void main(String[] args) {
        loadAccounts();
        String server = "localhost";
        int port = 8000;
        boolean hasLoggedIn = false;
        try (Socket socket = new Socket(server, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String userId = in.readLine();

            boolean isMyTurn = userId.equals("user1");
            if (isMyTurn) {
                System.out.println("--- You are User 1. You go first!");
            } else {
                System.out.println("--- You are User 2. Pick after User 1's turn!");
            }

            String status = in.readLine();
            do {
                System.out.print("""
                        [1] Login
                        [2] Signup
                        """);
                int c = menuValidation();
                sc.nextLine();
                switch (c) {
                    case 1:
                        System.out.print("Enter your username: ");
                        String userName = sc.nextLine();
                        if (userId.equals("user1")) {
                            user1Name = userName;
                        } else {
                            user2Name = userName;
                        }
                        System.out.print("Enter your password: ");
                        int password = sc.nextInt();
                        sc.nextLine();
                        boolean doesExist = checkAccount(userName, password);
                        if (doesExist) {
                            hasLoggedIn = true;
                            out.println("logged in");
                        } else {
                            System.out.println("Account not found!");
                            continue;
                        }
                        break;
                    case 2:
                        System.out.print("Set username: ");
                        userName = sc.nextLine();
                        System.out.print("Set password: ");
                        password = sc.nextInt();
                        sc.nextLine();
                        signUp(userName, password);
                        if (userId.equals("user1")) {
                            user1Name = userName;
                        } else {
                            user2Name = userName;
                        }
                        continue;
                    default:
                        System.out.println("Invalid input...");
                        continue;
                }
            } while (!hasLoggedIn);
            if (status.equals("READY")) {
                System.out.println("""
                            ---Connection established! Game has started!
                            Type only: rock paper scissors
                            Type: /quit to quit
                        """);
            }
            String scoreString1 = "", scoreString2 = "";
            while (true) {
                String serverMessage = in.readLine();
                if (serverMessage == null)
                    break;

                if (serverMessage.equals("your turn")) {
                    String pick = getValidPick();
                    out.println(pick);

                } else if (serverMessage.equals("wait")) {
                    System.out.println("Waiting...");

                } else if (serverMessage.contains("RESULTS")) {
                    System.out.println("Results received!");

                } else if (serverMessage.contains("CLIENT 1:")) {
                    scoreString1 = serverMessage.substring(10).trim();
                    c1Score = Integer.parseInt(scoreString1);
                } else if (serverMessage.contains("CLIENT 2:")) {
                    scoreString2 = serverMessage.substring(10).trim();
                    c2Score = Integer.parseInt(scoreString2);
                } else {
                    System.out.println(serverMessage);
                }
            }
            loadAccounts();
            setScores(user1Name, user2Name, c1Score, c2Score);
            saveAccounts();
            showLeaderboard();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScores(String user1Name, String user2Name, int c1Score, int c2Score) {
        for (Players p : players) {
            if (p.getUsername().equals(user1Name)) {
                p.setWins(p.getWins() + c1Score);
                p.setLosses(p.getLosses() + c2Score);
            } else if (p.getUsername().equals(user2Name)) {
                p.setWins(p.getWins() + c2Score);
                p.setLosses(p.getLosses() + c1Score);
            }
        }
    }

    public static int menuValidation() {
        int i;
        while (true) {
            try {
                System.out.print("Choice: ");
                i = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input... try again");
                sc.next();
                continue;
            } catch (NullPointerException e) {
                System.out.println("No input detected... try again");
                continue;
            }
        }
        return i;
    }

    public static String getValidPick() {
        while (true) {
            System.out.print("Your pick: ");
            String input = sc.nextLine().toLowerCase();

            if (input.equals("rock") || input.equals("paper") || input.equals("scissor") || input.equals("/quit")) {
                return input;
            } else {
                System.out.println("Invalid pick... try again");
            }
        }
    }

    public static boolean checkAccount(String username, int password) {
        for (Players p : players) {
            if (username.equals(p.getUsername()) && password == p.getPassword()) {
                return true;
            }
        }
        return false;
    }

    public static void loadAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/players.json"))) {
            Type playerListType = new TypeToken<ArrayList<Players>>() {
            }.getType();
            Gson gson = new Gson();

            players = gson.fromJson(br, playerListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUp(String username, int password) {
        Players p = new Players();
        p.setUsername(username);
        p.setPassword(password);
        p.setWins(0);
        p.setLosses(0);
        p.setWinrate(0);
        players.add(p);
    }

    public static void saveAccounts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/players.json"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.toJson(players, bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showLeaderboard() { // NAME WINS LOSSES WINRATE
        System.out.printf("%-9s %-6s %-9s %-7s\n", "NAME", "WINS", "LOSSES", "WINRATE");
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(i).getWinrate() < players.get(j).getWinrate()) {
                    Players s;
                    s = players.get(j);
                    players.set(j, players.get(i));
                    players.set(i, s);
                }
            }
        }
        for (Players p : players) {
            System.out.printf("%-9s %-6d %-9d %-7.2f\n", p.getUsername(), p.getWins(), p.getLosses(), p.getWinrate());
        }
    }
}