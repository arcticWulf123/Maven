package org.example.Services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.example.Models.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class UserService {
    private static UserService instance;
    private List<User> users = new ArrayList<>();
    private List<String> loggedInUsers = new ArrayList<>();
    public static final String filePath = "data/users.json";

    public UserService() {
        // loadUsers();
    }

    public synchronized boolean doesExist(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public synchronized User login(String username, String password) {
        if (loggedInUsers.contains(username)) {
            System.out.println("This user is already logged in!");
            return null;
        }

        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                loggedInUsers.add(username); // mark as logged in
                return u; // return the matched User object
            }
        }

        System.out.println("Invalid username or password.");
        return null; // no match found
    }

    public synchronized void signup(String username, String password) {
        User u = new User(username, password);
        users.add(u);
    }

    public synchronized void saveData() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(filePath))) {
            RuntimeTypeAdapterFactory<AccountEntry> accountFactory = RuntimeTypeAdapterFactory
                    .of(AccountEntry.class, "type")
                    .registerSubtype(SocialAccount.class, "social account")
                    .registerSubtype(BankAccount.class, "bank account")
                    .registerSubtype(EmailAccount.class, "email account");

            Gson gson = new GsonBuilder().registerTypeAdapterFactory(accountFactory).create();
            gson.toJson(users, br);
        } catch (IOException e) {
            System.out.println("Error saving user data to json...");
        }
    }

    public synchronized void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            RuntimeTypeAdapterFactory<AccountEntry> accountFactory = RuntimeTypeAdapterFactory
                    .of(AccountEntry.class, "type")
                    .registerSubtype(SocialAccount.class, "social account")
                    .registerSubtype(BankAccount.class, "bank account")
                    .registerSubtype(EmailAccount.class, "email account");

            Gson gson = new GsonBuilder().registerTypeAdapterFactory(accountFactory).create();
            Type accountList = new TypeToken<ArrayList<AccountEntry>>() {
            }.getType();
            users = gson.fromJson(br, accountList);
        } catch (IOException e) {
            System.out.println("Error saving user data to json...");
        }
    }

}
