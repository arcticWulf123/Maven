package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.example.model.Person;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        
    }

    public static void uno() throws FileNotFoundException {
        // convert json file to object
        String json = "";
        Scanner sc = new Scanner(new File("data/Person.json"));
        while (sc.hasNextLine()) {
            json += sc.nextLine();
        }
        Gson gson = new Gson();
        Person p1 = gson.fromJson(json, Person.class);
        System.out.println(p1.toString());
    }

    public static void dos() {
        // convert object to json file
        try (FileWriter fw = new FileWriter("data/NewPerson.json")) {
            Person p2 = new Person("Harry", "Osborn", 0, "harryosborn@gmail.com", "092131423", "01-01-2000",
                    "17 Newark Street", false, "Romanian", "Male");
            Gson gson2 = new Gson();
            gson2.toJson(p2, fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}