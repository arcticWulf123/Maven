package org.example.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
            int user = Integer.parseInt(in.readLine());
            switch (user) {
                case 1:
                    
                case 2:

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
