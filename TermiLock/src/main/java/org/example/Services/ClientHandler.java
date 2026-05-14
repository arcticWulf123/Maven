package org.example.Services;

import java.io.*;
import java.net.Socket;

import org.example.Models.User;

/*
TO DO 
1. ClientHandler
2. Server
3. Client
*/
public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            UserService userService = new UserService();
            AccountService accountService = new AccountService();
            User u = null;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            boolean isLoggedIn = false;
            /*
             * 1. Menu not being entirely sent, client only reads one readLine
             * 2. Login logic
             * 3. Signup logic
             * 4. Exit logic
             */

            while (!isLoggedIn) {
                out.println("Welcome to TermiLock! [1] Login [2] Signup [3] Exit");
                int user = Integer.parseInt(in.readLine());
                switch (user) {
                    case 1:
                        out.println("Enter your username: ");
                        String username = in.readLine();
                        out.println("Enter your password: ");
                        String password = in.readLine();
                        u = userService.login(username, password);
                        if (u != null) {
                            isLoggedIn = true;
                        } else {
                            out.println("Invalid credentials");
                            continue;
                        }
                        break;
                    case 2:
                        out.println("Set your username: ");
                        username = in.readLine();
                        out.println("Set your password: ");
                        password = in.readLine();
                        userService.signup(username, password);
                        continue;
                    case 3:
                        out.println("exit");
                        break;
                }
            }

            boolean hasLoggedout = false;

            do {
                out.println("Welcome to TermiVault, " + u.getUsername());
            } while (!hasLoggedout);

        } catch (IOException e) {
            System.err.print("Error! could not retrieve needed data...");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.print("Error! could not close the socket");
            }
        }
    }
}