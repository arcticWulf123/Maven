package com.grades;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.grades.model.Grades;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static List<Grades> gradesList = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("""
                    MAIN MENU
                    [1] Add grades for a subject
                    [2] Display Grades
                    [3] Exit
                    [4] Save to JSON
                    """);
            System.out.print("Enter choice: ");
            int response = inputHandler();
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
                case 4:
                    saveData();
                    System.out.println("Data saved...");
                    break;
            }
        }

    }

    public static void enterGradesMenu() {
        String response = "";
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
            gradesList.add(g);
        } while (response.contains("y"));
    }

    public static void displayGrades() {
        try (FileReader fr = new FileReader("data/grades.json")) {
            Type gradesListType = new TypeToken<ArrayList<Grades>>() {
            }.getType();
            Gson gson = new Gson();
            List<Grades> grades = gson.fromJson(fr, gradesListType);
            for (Grades g : grades) {
                System.out.println(g.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int inputHandler() {
        int number = 0;

        Scanner input = new Scanner(System.in);

        for (;;) {
            try {
                number = input.nextInt();

                return number;
            } catch (InputMismatchException e) {
                input.nextLine();
                System.out.print("Invalid number... try again!\nEnter a number: ");
            }
        }
    }

    public static void saveData() {
        try (FileWriter fw = new FileWriter("data/grades.json")) {
            Gson gb = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gb.toJson(gradesList, fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}