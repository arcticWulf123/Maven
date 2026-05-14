package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import org.example.Services.Lockdown;

public class Client {
    public final static String server = "localhost";
    public final static int port = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(server, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in)) {
            while (true) {
                String message = in.readLine();

                System.out.println(message);

                if (message.equals("exit")) {
                    System.exit(1);
                }

                String response = sc.nextLine();
                out.println(response);
            }
        } catch (IOException e) {
            System.err.print("Error! Could not connect to the server...");
        }
    }

    public static void startAutolock() {
        Lockdown lock = new Lockdown();
        lock.start();
        try {
            Lockdown.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Could not load Thread");
        }
    }
}
