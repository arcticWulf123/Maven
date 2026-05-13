package org.example.Models;

public class SocialAccount extends AccountEntry implements Encryptable{
    private String username;
    private String platform;
    private String password;

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPlatform() {
        return platform;
    }


    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public SocialAccount(String username, String platform, String password) {
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
