package org.example.Models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<AccountEntry> accounts = new ArrayList<>();

    public synchronized void setAccounts(List<AccountEntry> accounts) {
        this.accounts = accounts;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized void setUsername(String username) {
        this.username = username;
    }

    public synchronized String getPassword() {
        return password;
    }

    public synchronized void setPassword(String password) {
        this.password = password;
    }

    public synchronized ArrayList<AccountEntry> getAccounts() {
        return (ArrayList<AccountEntry>) accounts;
    }

    public synchronized void addAccountEntry(AccountEntry e) {
        accounts.add(e);
    }
}
