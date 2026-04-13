package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Grades;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiThreadedListApp {
    public static List<Grades> data = new CopyOnWriteArrayList<>();
    public static Scanner sc = new Scanner(System.in);
    static void main() {
        Scanner input = new Scanner(System.in);
        loadData();
        // thread 1
        Thread saver = new Thread(()->{
            //this is the run method, put here the things you want to do
            while(true){
                //save the list
                saveToDisk();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        Thread fetcher = new Thread(()->{
            while(true){
                //read file
                readFile();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        //set both as daemon so they close when main is terminated
        saver.setDaemon(true);
        fetcher.setDaemon(true);
        saver.start();
        fetcher.start();

        //MENU
        while (true) {
            System.out.println("""
                    MAIN MENU
                    [1] Add grades for a subject
                    [2] Display Grades
                    [3] Exit
                    """);
            System.out.print("Enter choice: ");
            int response = input.nextInt(); input.nextLine();
            switch (response) {
                case 1:
                    enterGradesMenu();
                    break;
                case 2:
                    displayGrades();
                    break;
                case 3:
                    System.out.println("Exiting program... Goodbye!");
                    System.exit(0);
                    break;
            }
        }

    }

    public static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/grades.json"))) {
            Type datalistType = new TypeToken<List<Grades>>(){}.getType();
            Gson gson = new Gson();
            data = gson.fromJson(br, datalistType);
            if (data == null) {
                data = new CopyOnWriteArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("File not found");
            data = new CopyOnWriteArrayList<>();
        }
    }


    public static void saveToDisk(){
        try (FileWriter fw = new FileWriter("src/main/java/data/grades.json")) {
            Gson gb = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gb.toJson(data, fw);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static void readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/grades.json"))) {
            Type dataRead = new TypeToken<List<Grades>>(){}.getType();

            Gson gson = new Gson();
            data = gson.fromJson(br, dataRead);
            if (data == null) {
                data = new CopyOnWriteArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
    public static void enterGradesMenu() {
        String response;
        if (data == null) {
            data = new CopyOnWriteArrayList<>();
        }
        do {
            Grades g = new Grades();
            System.out.print("Enter the subject name: ");
            g.setSubjectName(sc.nextLine());
            System.out.print("Enter prelim grade for subject:");
            g.setPrelimGrade(sc.nextDouble());
            sc.nextLine();
            System.out.print("Enter midterm grade for subject:");
            g.setMidtermGrade(sc.nextDouble());
            sc.nextLine();
            System.out.print("Enter final grade for subject: ");
            g.setFinalGrade(sc.nextDouble());
            sc.nextLine();
            System.out.print("Add another subject? (y/n): ");
            response = sc.nextLine();
            data.add(g);
        } while (response.contains("y"));
    }

    public static void displayGrades() {
        for (Grades g : data) {
            System.out.println(g.toString());
        }
    }
}
