package org.example.Models;

public class EmailAccount extends AccountEntry implements Encryptable{
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmailAccount(String email, String password) {
        this.email = email;
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
        return String.format("%s:%s",email, password);
    }
}
