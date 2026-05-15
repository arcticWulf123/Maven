package org.example.Services;

import java.io.*;
import java.net.Socket;

import org.example.Models.AccountEntry;
import org.example.Models.BankAccount;
import org.example.Models.EmailAccount;
import org.example.Models.PasswordGenerator;
import org.example.Models.SocialAccount;
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
            PasswordGenerator passwordGenerator = new PasswordGenerator();
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
                        [2] Remove account entry
                        [3] View account entries
                        [4] Generate password
                        [5] Update account entry
                        [6] Logout
                        Choice:
                        """, u.getUsername()));
                out.println("END");
                int choice = Integer.parseInt(in.readLine());
                switch (choice) {
                    case 1:
                        out.println("""
                                [1] Bank Account
                                [2] Email Account
                                [3] Social Account
                                Choice:
                                """);
                        out.println("END");
                        choice = Integer.parseInt(in.readLine());
                        switch (choice) {
                            case 1:
                                out.println("Enter bank number: ");
                                out.println("END");
                                int banknumber = Integer.parseInt(in.readLine());
                                out.println("Enter pin: ");
                                out.println("END");
                                String pin = in.readLine();
                                BankAccount a = new BankAccount(banknumber, pin);
                                u.addAccountEntry(a);
                                break;
                            case 2:
                                out.println("Enter email address: ");
                                out.println("END");
                                String email = in.readLine();
                                out.println("Enter password: ");
                                out.println("END");
                                String password = in.readLine();
                                EmailAccount e = new EmailAccount(email, password);
                                u.addAccountEntry(e);
                                break;
                            case 3:
                                out.println("Enter platform");
                                out.println("END"); 
                                String platform = in.readLine();
                                out.println("Enter username: ");
                                out.println("END");
                                String username = in.readLine();
                                out.println("Enter password: ");
                                out.println("END");
                                password = in.readLine();
                                SocialAccount s = new SocialAccount(username, platform, password);
                                u.addAccountEntry(s);
                            default:
                                break;
                        }
                        break;
                    case 2:
                        for (AccountEntry e : u.getAccounts()) {
                            out.println(e.toString());
                        }
                        break;
                    case 3:
                        //TODO: Create a function to view every account (by category)
                        break;
                    case 4:
                        out.println("Enter password length: ");
                        out.println("END");
                        int length = Integer.parseInt(in.readLine());
                        out.println("Generated password: " + passwordGenerator.generate(length));
                        out.println("END");
                        continue;
                    case 5:
                        //TODO: Create a function to update accounts
                    case 6:
                        userService.saveData();
                        out.println(String.format("%s logging out...", u.getUsername()));
                        out.println("exit");
                        out.println("END");
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