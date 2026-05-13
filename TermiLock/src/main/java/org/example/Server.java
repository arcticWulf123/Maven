package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.example.Services.ClientHandler;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client has connected!");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.print("Error... could not fetch clients");
        }
    }
}
