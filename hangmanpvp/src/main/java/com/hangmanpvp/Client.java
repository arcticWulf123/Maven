package com.hangmanpvp;

import com.hangmanpvp.Service.PlayerService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static Scanner sc = new Scanner(System.in);
    public static PlayerService playerService = new PlayerService();
    public static void main(String[] args) {
        String server = "localhost";
        int port = 8000;

        try (
                Socket socket = new Socket(server, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
                    String serverMessage = in.readLine();
                    System.out.println(serverMessage);
                    while (true) {
                        System.out.print("""
                                [1] Login
                                [2] Sign Up
                                [3] Show Leaderboard
                                Choice: """);
                        int choice = sc.nextInt();
                        sc.nextLine();
                        out.println(choice);
                        switch (choice) {
                            case 1:
                                System.out.print("Enter your username: ");
                                String username = sc.nextLine();
                                out.println(username);

                                System.out.print("Enter your password: ");
                                int password = sc.nextInt();
                                sc.nextLine();
                                out.println(password);

                                String response = in.readLine();
                                System.out.println(response);

                                if (response.equals("Invalid username or password")) {
                                    continue;
                                }
                                break;
                            case 2:
                                System.out.print("Set your username: ");
                                username = sc.nextLine();
                                System.out.print("Set your password:");
                                password = sc.nextInt();
                                out.println(username);
                                out.println(password);
                                continue;
                            case 3:
                                playerService.showLeaderBoard();
                                continue;
                        }
                        break;
                    }
                    while (true) {
                        String currentState = in.readLine();
                        if (currentState.contains("Game over!")) {
                            System.out.println(in.readLine());
                            break;
                        }
                        System.out.print("Guess a letter in " + currentState + ": ");

                        char guess = sc.nextLine().charAt(0);
                        out.println(guess);

                        String response = in.readLine();
                        System.out.println(response);
                    }

                } catch (IOException e) {
            System.out.println("Disconnected from server");
            }
        }
    }