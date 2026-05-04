package org.example.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.Models.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/*
This class is to handle logic of passwords such as logging in and validating it
 */
public class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        loadUsers();
    }

    public boolean doesExist(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) &&  user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void saveData(){

    }

    public void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/users.json"))) {
            Type usersList = new TypeToken<ArrayList<User>>() {
            }.getType();
            Gson gson = new Gson();
            users = gson.fromJson(br, usersList);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

}
