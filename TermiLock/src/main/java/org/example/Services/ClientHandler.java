package org.example.Services;

import java.io.*;
import java.net.Socket;

import org.example.Models.User;

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
            UserService userService = UserService.getInstance();
            AccountService accountService = new AccountService();
            User u = null;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            boolean isLoggedIn = false;
            while (!isLoggedIn) {
                out.println("""
                        Welcome to TermiLock!
                        [1] Login
                        [2] Signup
                        [3] Exit
                        Choice: """);
                out.println("END");
                int user = Integer.parseInt(in.readLine());
                switch (user) {
                    case 1:
                        out.println("Enter your username: ");
                        out.println("END");
                        String username = in.readLine();
                        out.println("Enter your password: ");
                        out.println("END");
                        String password = in.readLine();
                        u = userService.login(username, password);
                        if (u != null) {
                            isLoggedIn = true;
                        } else {
                            out.println("Invalid credentials");
                            out.println("END");
                            continue;
                        }
                        break;
                    case 2:
                        out.println("Set your username: ");
                        out.println("END");
                        username = in.readLine();
                        out.println("Set your password: ");
                        out.println("END");
                        password = in.readLine();
                        userService.signup(username, password);
                        continue;
                    case 3:
                        out.println("exit");
                        out.println("END");
                        break;
                }
            }

            boolean hasLoggedout = false;
            do {
                out.println(String.format("""
                        Welcome to TermiVault, %s!
                        [1] Add account entry
                        [2] Delete account entry
                        [3] View account entries
                        [4] Generate password
                        [5] Update account entry
                        [6] Search account entry
                        [7] Logout
                        Choice:
                        """, u.getUsername()));
                out.println("END");
                int choice = Integer.parseInt(in.readLine());
                switch (choice) {
                    case 1:
                        accountService.addAccountEntry(socket, out, in, u);
                        break;
                    case 2:
                        accountService.deleteAccountEntry(socket, out, in, u);
                        break;
                    case 3:
                        accountService.viewAccountEntries(socket, out, in, u);
                        break;
                    case 4:
                        accountService.generatePassword(socket, out, in);
                        break;
                    case 5:
                        accountService.updateAccountEntry(socket, out, in, u);
                        break;
                    case 6:
                        accountService.searchAccount(out, in, u);
                        break;
                    case 7:
                        userService.saveData();
                        out.println(String.format("%s logging out...", u.getUsername()));
                        hasLoggedout = true;
                        out.println("END");
                        break;
                    default:
                        break;
                }

            } while (!hasLoggedout);

        } catch (IOException e) {
            System.err.print("Error! could not retrieve needed data...");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.print("Error! could not close the socket...");
            }
        }
    }
}