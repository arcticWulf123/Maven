package org.example.Models;

public interface Encryptable {
    int SHIFT = 5; 

    default String encrypt(String entry) {
        char[] chars = entry.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] += SHIFT;
        }
        return String.valueOf(chars);
    }

    default String decrypt(String entry) {
        char[] chars = entry.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] -= SHIFT;
        }
        return String.valueOf(chars);
    }
}
