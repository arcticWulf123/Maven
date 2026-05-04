package com.roshambo;

import java.io.*;
import java.net.*;

import com.roshambo.models.*;

public class Server {

    public static void main(String[] args) {

        int port = 8000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Waiting for both players to be connected...");
            Socket client1 = serverSocket.accept();
            PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            System.out.println("User 1 is connected.");

            out1.println("user1");

            Socket client2 = serverSocket.accept();

            PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));

            System.out.println("User 2 connected.");
            out2.println("user2");

            System.out.println("Both can now message each other");
            out1.println("READY");
            out2.println("READY");

            do {
                // accepts username and password from client1
                System.out.println("Waiting for User1 login...");
                String c1Status = in1.readLine();
                // accepts username and password from client2
                System.out.println("Waiting for User2 login...");
                String c2Status = in2.readLine();
                if (c1Status.contains("logged in") && c2Status.contains("logged in"))
                    break;
            } while (true);
            int rounds = 1;
            GameMove c1Move = null;
            GameMove c2Move = null;
            int c1Score = 0, c2Score = 0;
            String winningClient = "";
            while (rounds <= 10) {
                // USER 1 TURN
                out1.println("your turn");
                out2.println("wait");

                String user1Message = in1.readLine();
                switch (user1Message) {
                    case "rock":
                        c1Move = new Rock(user1Message);
                        break;
                    case "paper":
                        c1Move = new Paper(user1Message);
                        break;
                    case "scissor":
                        c1Move = new Scissor(user1Message);
                        break;
                }
                if (user1Message.contains("/quit"))
                    break;

                out2.println("User 1 has chosen");

                // USER 2 TURN
                out2.println("your turn");
                out1.println("wait");

                String user2Message = in2.readLine();
                if (user2Message == null)
                    break;
                switch (user2Message) {
                    case "rock":
                        c2Move = new Rock(user2Message);
                        break;
                    case "paper":
                        c2Move = new Paper(user2Message);
                        break;
                    case "scissor":
                        c2Move = new Scissor(user2Message);
                        break;
                }
                out1.println("User 2 has chosen");
                int result = c1Move.compare(c2Move);

                if (result > 0) {
                    c1Score++;
                    winningClient = "CLIENT 1 wins the game!";
                } else if (result < 0) {
                    c2Score++;
                    winningClient = "CLIENT 2 wins the game!";
                }
                rounds++;
            }
            if (c1Score == c2Score || c1Score == 0 && c2Score == 0) {
                winningClient = "Match is a draw";
            }
            out1.printf("""
                    ------RESULTS------
                    CLIENT 1: %d
                    CLIENT 2: %d
                    %s
                    \n
                    """, c1Score, c2Score, winningClient);
            out2.printf("""
                    ------RESULTS------
                    CLIENT 1: %d
                    CLIENT 2: %d
                    %s
                    \n
                    """, c1Score, c2Score, winningClient);

            out1.close();
            out2.close();
            in1.close();
            in2.close();
            client1.close();
            client2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}