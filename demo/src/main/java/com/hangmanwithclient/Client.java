package com.hangmanwithclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String server = "192.168.4.158"; // same as 127.0.0.1
        int port = 8000;

        try (Socket socket = new Socket(server, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in);) {
            String user = "";
            System.out.println("Connected to the server. Welcome");

            while (true) {
                System.out.print("""
                        [1] Login
                        [2] Signup
                        [3] Leaderboard
                        Choice: """);
                int reply = sc.nextInt();
                sc.nextLine();
                switch (reply) {
                    case 1:
                        System.out.print("Enter username: ");
                        String username = sc.nextLine();
                        out.println(username);
                        System.out.print("Enter password: ");
                        int password = sc.nextInt();
                        out.println(password);
                        break;
                }

                // determine if server's quitting
                if (reply == 0) {
                    System.out.println("Server disconnected...");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Can't connect right now...");
        }

    }

    public static void menu() {
        System.out.print("""
                [1] Login
                [2] Signup
                [3] Leaderboard
                Choice: """);
    }
}