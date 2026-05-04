package org.example.Models;

public class SocialKey extends PasswordEntry implements Encryptable{
    private String username;
    private String platform;
    private String password;

    public SocialKey(String username, String platform, String password) {
        this.username = username;
        this.platform = platform;
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
        return String.format("%s:%s:%s", username, platform, password);
    }
}
