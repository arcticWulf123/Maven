package org.example;

import java.io.*;
import java.net.*;

import org.example.Services.ClientHandler;

public class Server {
    public final static int port = 8080;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("New client has connected!");
                out.println("Successfully connected!");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.print("Error... could not fetch clients");
        }
    }
}
