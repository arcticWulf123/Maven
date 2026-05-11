package org.example.Services;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import org.example.Models.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class UserService {
    private List<User> users = new ArrayList<>();
    public static final String filePath = "data/users.json";

    public UserService() {
        loadUsers();
    }

    public boolean doesExist(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void saveData() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(filePath))) {
            RuntimeTypeAdapterFactory<AccountEntry> accountFactory = RuntimeTypeAdapterFactory
                    .of(AccountEntry.class, "type")
                    .registerSubtype(SocialAccount.class, "social account")
                    .registerSubtype(BankAccount.class, "bank account")
                    .registerSubtype(EmailAccount.class, "email account");

            Gson gson = new GsonBuilder().registerTypeAdapterFactory(accountFactory).create();
            gson.toJson(users, br);
        } catch (IOException e) {
            System.out.println("Error saving data...");
        }
    }

    public void loadUsers() {
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
            System.out.println("Error saving data...");
        }
    }

}
