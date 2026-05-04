package org.example;

import org.example.Services.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static void main() {
        System.out.println("Server has started");
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            while (true) {
                Socket client = serverSocket.accept(); // accepts clients
                System.out.println("New Client Connected: " + client);
                ClientHandler handler = new ClientHandler(client); // create separate worker thread for client
                new Thread(handler).start(); // starts the worker thread for that client
            }
        } catch (IOException e) {
            System.err.println("Server cannot be initiated...");
        }
    }
    }
