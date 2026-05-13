package org.example.Models;

public class PasswordGenerator {
    static String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    static String DIGITS = "0123456789";
    static String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public static String generate(int length) {
        String charset = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;
        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * charset.length());
            password[i] = charset.charAt(index);
        }

        return new String(password);
    }
}
