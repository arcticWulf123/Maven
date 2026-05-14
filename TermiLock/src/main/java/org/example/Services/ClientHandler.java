package org.example.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Welcome to TermiLock!\n[1] Login\n[2] Signup\n[3] Exit\nChoice:");
            int user = Integer.parseInt(in.readLine());
            /*
            1. Menu not being entirely sent, client only reads one readLine
            2. Login logic
            3. Signup logic
            4. Exit logic
            */
            switch (user) {
                case 1:
                    out.println("Enter your username: ");
                    String username = in.readLine();
                    out.println("Enter your password: ");
                    String password = in.readLine();
                    User u = userService.login(username, password);
                    break;
                case 2:
                    out.println("Set your username: ");
                    username = in.readLine();
                    out.println("Set your password: ");
                    password = in.readLine();
                    userService.signup(username, password);
                    break;
            }
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
