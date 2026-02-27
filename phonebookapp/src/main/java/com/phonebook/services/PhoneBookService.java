package com.phonebook.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.phonebook.models.Contact;

public class PhoneBookService {
    private HashMap<String, Contact> contacts = new HashMap<>(); // let name be key

    public void addContact(Contact c) {
        contacts.put(c.getName(), c);
    }

    public void searchContact(String name) {
        Contact retrieveContact = contacts.get(name);
        if (contacts.containsKey(name)) {
            System.out
                    .println(retrieveContact.getName() + retrieveContact.getEmail() + retrieveContact.getPhoneNumber());
        } else {
            System.out.println("Sorry..." + name + " is not in the contacts list.");
        }
    }

    public void removeContact(String name) {
        if (contacts.containsKey(name)) {
            contacts.remove(name);
        } else {
            System.out.println("Name not found...");
        }
    }

    public void saveToCSV(String fileName) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
            String key = entry.getKey();
            Contact c = entry.getValue();
            sb.append("\n").append(key).append(",").append(c.getPhoneNumber()).append(",").append(c.getEmail());
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                Contact c = new Contact();
                c.setName(arr[0]);
                c.setEmail(arr[2]);
                c.setPhoneNumber(arr[1]);
                contacts.put(c.getName(), c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayAll() {
        System.out.printf("%-9s %-9s %-3s \n", "Name", "Phone Number", "Email");
        for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
            String key = entry.getKey();
            Contact c = entry.getValue();
            System.out.printf("%-8s %-12s %-3s \n", key, c.getPhoneNumber(), c.getEmail());
        }
        System.out.printf("");
    }
}
