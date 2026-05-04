package org.example.Models;

public class EmailKey extends PasswordEntry implements Encryptable{
    private String gmail;
    private String password;

    public EmailKey(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
    }

    @Override
    public String getKey() {
        return password;
    }

    @Override
    public void encrypt(String entry) {
        Encryptable.super.encrypt(entry);  // calls the default method
    }

    @Override
    public String decrypt(String entry) {
        return Encryptable.super.decrypt(entry);  // calls the default method
    }
    @Override
    public String toString() {
        return String.format("%s:%s",gmail, password);
    }
}
