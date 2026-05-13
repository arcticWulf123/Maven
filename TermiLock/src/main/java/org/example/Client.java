package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public final static String server = "localhost";
    public final static int port = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(server, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in)) {
                    while (true) {

                    }
        } catch (IOException e) {
            System.err.print("");
        }
    }
}
