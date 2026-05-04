package org.example.Models;

public interface Encryptable {
    public default void encrypt(String entry) {
        char [] chars = entry.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += 1;
        }
    }
    public default String decrypt(String entry) {
        char [] chars = entry.toCharArray();
        for (int i  = 0; i < chars.length; i++) {
            chars[i] -= 1;
        }
        return  String.valueOf(chars);
    }
}
