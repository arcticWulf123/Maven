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
        loadUsers();
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

            // plain gson, no encryption factory
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(accountFactory)
                    .create();

            // serialize normally first
            Type userList = new TypeToken<ArrayList<User>>() {
            }.getType();
            JsonElement tree = gson.toJsonTree(users, userList);

            // then encrypt manually
            encryptTree(tree);

            br.write(gson.toJson(tree));

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

            // plain gson, no encryption factory
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(accountFactory)
                    .create();

            // parse json first
            JsonElement tree = JsonParser.parseReader(br);

            // decrypt manually before deserializing
            decryptTree(tree);

            Type userList = new TypeToken<ArrayList<User>>() {
            }.getType();
            users = gson.fromJson(tree, userList);

            if (users == null)
                users = new ArrayList<>();

        } catch (FileNotFoundException e) {
            System.out.println("No existing data found, starting fresh...");
            users = new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error loading user data from json...");
        }
    }

    private void encryptTree(JsonElement element) {
        if (element.isJsonArray()) {
            for (JsonElement e : element.getAsJsonArray())
                encryptTree(e);
        } else if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            for (String key : new HashSet<>(obj.keySet())) {
                if (key.equals("type"))
                    continue; // ← never encrypt type
                JsonElement el = obj.get(key);
                if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isString()) {
                    obj.addProperty(key, encrypt(el.getAsString()));
                } else {
                    encryptTree(el); // recurse into nested objects/arrays
                }
            }
        }
    }

    private void decryptTree(JsonElement element) {
        if (element.isJsonArray()) {
            for (JsonElement e : element.getAsJsonArray())
                decryptTree(e);
        } else if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            for (String key : new HashSet<>(obj.keySet())) {
                if (key.equals("type"))
                    continue; // ← never decrypt type
                JsonElement el = obj.get(key);
                if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isString()) {
                    obj.addProperty(key, decrypt(el.getAsString()));
                } else {
                    decryptTree(el); // recurse into nested objects/arrays
                }
            }
        }
    }

    private String encrypt(String entry) {
        char[] chars = entry.toCharArray();
        for (int i = 0; i < chars.length; i++)
            chars[i] += 5;
        return String.valueOf(chars);
    }

    private String decrypt(String entry) {
        char[] chars = entry.toCharArray();
        for (int i = 0; i < chars.length; i++)
            chars[i] -= 5;
        return String.valueOf(chars);
    }
}
