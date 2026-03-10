package com.grades;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grades.model.Grades;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static Grades compro2 = new Grades();
    public static Grades dsa = new Grades();
    public static Grades oop = new Grades();

    public static void main(String[] args) {

        while (true) {
            System.out.println("""
                    MAIN MENU
                    [1] Enter Grades
                    [2] Display Grades
                    [3] Exit
                    """);
            System.out.print("Enter choice: ");
            int response = inputHandler();
            switch (response) {
                case 1:
                    System.out.print("Enter grade for: \n");
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

    private static void enterGradesMenu() {
        boolean isTrue = false;
        do {

            System.out.print("""
                    [1] COMPRO2
                    [2] DSA
                    [3] OOP
                    [0] GO BACK \n
                    """);
            System.out.print("Enter choice: ");
            int response2 = sc.nextInt();

            switch (response2) {
                case 1:
                    compro2.setSubjectName("COMPRO2");
                    System.out.print("""
                            Enter prelim grade for COMPRO2
                            """);
                    compro2.setPrelimGrade(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("""
                            Enter midterm grade for COMPRO2
                            """);
                    compro2.setMidtermGrade(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("""
                            Enter final grade for COMPRO2
                            """);
                    compro2.setFinalGrade(sc.nextDouble());
                    sc.nextLine();
                    break;
                case 2:
                    dsa.setSubjectName("DSA");
                    System.out.print("""
                            Enter prelim grade for DSA
                            """);
                    dsa.setPrelimGrade(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("""
                            Enter midterm grade for DSA
                             """);
                    dsa.setMidtermGrade(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("""
                            Enter final grade for DSA
                            """);
                    dsa.setFinalGrade(sc.nextDouble());
                    sc.nextLine();
                    break;
                case 3:
                    oop.setSubjectName("OOP");
                    System.out.print("""
                            Enter prelim grade for OOP
                            """);
                    oop.setPrelimGrade(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("""
                            Enter midterm grade for OOP
                            """);
                    oop.setMidtermGrade(sc.nextDouble());
                    sc.nextLine();
                    System.out.print("""
                            Enter final grade for OOP
                            """);
                    oop.setFinalGrade(sc.nextDouble());
                    sc.nextLine();
                    break;
                case 0:
                    isTrue = true;
            }

        } while (!isTrue);
    }

    public static void displayGrades() {
        System.out.println("""
                     --- GRADE TABLE ---
                (Table to be implemented later)
                        """);
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
        List<Grades> gradesList = new ArrayList<>();
        gradesList.add(compro2);
        gradesList.add(dsa);
        gradesList.add(oop);
        try (FileWriter fw = new FileWriter("data/grades.json")) {
            Gson gb = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gb.toJson(gradesList, fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}