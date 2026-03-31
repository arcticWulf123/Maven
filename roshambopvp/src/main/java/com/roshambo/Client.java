package com.roshambo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String server = "localhost";
        int port = 8000;

        try (Socket socket = new Socket(server, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner sc = new Scanner(System.in);
            String userId = in.readLine();

            boolean isMyTurn = userId.equals("user1");
            if (isMyTurn) {
                System.out.println("--- You are User 1. You go first! But wait for the other user.");
            } else {
                System.out.println("--- You are User 2. Wait for User 1's turn!");
            }
            String status = in.readLine();
            if (status.equals("READY")) {
                System.out.println("---Connection established! Game has started");
            }
            System.out.println("Enter your username: ");
            String username = sc.nextLine();
            out.println(username);
            System.out.println("Enter your password: ");
            int password = sc.nextInt(); sc.nextLine();
            out.println(password);
            while (true) {

                if (isMyTurn) {
                    // send message first
                    System.out.print("Your pick: ");
                    String message = sc.nextLine();
                    out.println(message);

                    if (message.equalsIgnoreCase("/quit"))
                        break;

                    isMyTurn = false;
                    System.out.println("--- Waiting for other user...");
                } else {
                    // read other user message
                    String otherUserMessage = in.readLine();
                    if (otherUserMessage == null)
                        break;

                    System.out.println("Other user: " + otherUserMessage);
                    isMyTurn = true;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
