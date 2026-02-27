package com.phonebook;

import com.phonebook.models.Contact;
import com.phonebook.services.PhoneBookService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PhoneBookService pb = new PhoneBookService();
        pb.loadFromCSV("contacts.csv");
        while (true) {
            System.out
                    .print(" 1. Add \n 2. Search \n 3. Remove \n 4. Display all \n 5. Save to CSV \n 0. Exit \n Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    Contact c = new Contact();
                    System.out.print("Enter contact name: ");
                    c.setName(sc.nextLine());
                    System.out.print("Enter contact number: ");
                    int contactNo = sc.nextInt();
                    sc.nextLine();
                    String toString = Integer.toString(contactNo);
                    c.setPhoneNumber(toString);
                    System.out.print("Enter contact email: ");
                    c.setEmail(sc.nextLine());
                    pb.addContact(c);
                    continue;
                case 2:
                    System.out.print("Enter contact name to be searched: ");
                    String name = sc.nextLine();
                    pb.searchContact(name);
                    break;
                case 3:
                    System.out.print("Enter contact name to be deleted: ");
                    name = sc.nextLine();
                    pb.removeContact(name);
                    break;
                case 4:
                    pb.displayAll();
                    break;
                case 5:
                    pb.saveToCSV("contacts.csv");
                    System.out.println("Contacts saved...");
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}