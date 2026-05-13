package org.example.Services;

import java.io.IOException;

public class Lockdown {
    public void killScreen() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new ProcessBuilder("clear")
                        .inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                e.printStackTrace();
            }
        }
    }
}
