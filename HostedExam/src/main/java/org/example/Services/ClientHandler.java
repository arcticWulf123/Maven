package org.example.Services;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override
    public void run() {

        try {
            while (true) {
                System.out.println("""
                Welcome to the Exam! Hosted by Teacher Jade! Login first to continue...
                [1] Login
                [2] Exit
                Choice: """);
                int choice = Integer.parseInt(in.readLine());
                switch (choice) {
                    case 1:

                }
            }
        } catch (IOException e) {
            System.out.println("Error... Disconnected.");
        }
    }
}
