package com.hangmanpvp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String server = "localhost"; // same as 127.0.0.1
        int port = 8000;

        // 2: create a socket and connect to server
        try (Socket socket = new Socket(server, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in);) {
            // identifies the first user/client that's connected
            String userId = in.readLine();

            boolean isMyTurn = userId.equals("user1");

            if (isMyTurn) {
                System.out.println("--- You are User 1. You start the game! But wait for another user.");
            } else {
                System.out.println("--- You are User 2. Wait for User 1!");
            }

            String status = in.readLine();
            if (status.equals("READY")) {
                System.out.println("---Connection established! Start chatting now!");
            }

            if (isMyTurn) {
                System.out.println("Welcome User 1! ");
                System.out.println("""
                        [1] Login
                        [2] Signup
                        """);
                int c = sc.nextInt(); sc.nextLine();
                if (c == 1) {
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    int password = sc.nextInt();
                    out.println(username);
                    out.println(password);
                    System.out.println(in.readLine());
                } else if (c == 2) {
                    System.out.print("Set username: ");
                    String username = sc.nextLine();
                    System.out.println("Set password: ");
                    int password = sc.nextInt();
                    out.println(username);
                    out.println(password);
                    System.out.println(in.readLine());
                }
            } else {

                System.out.println("Welcome User 2! ");
                System.out.println("""
                        [1] Login
                        [2] Signup
                        """);
                int c = sc.nextInt(); sc.nextLine();
                if (c == 1) {
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    int password = sc.nextInt();
                    out.println(username);
                    out.println(password);
                    while (!in.readLine().contains("exiting")) {
                        System.out.println(in.readLine());
                        System.out.print("Your guess: ");
                        String guess = sc.nextLine();
                        out.println(guess);
                    }
                } else if (c == 2) {
                    System.out.print("Set username: ");
                    String username = sc.nextLine();
                    System.out.println("Set password: ");
                    int password = sc.nextInt();
                    out.println(username);
                    out.println(password);
                    isMyTurn = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Can't connect right now...");
        }
    }
}