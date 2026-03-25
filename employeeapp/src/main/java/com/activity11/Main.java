package com.activity11;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.activity11.model.*;
import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {

        System.out.print("""
                [1] Add Employee
                [2] View All Employees
                [3] Save Records
                [4] Load Records
                [5] Exit
                Choice: """);
        int c = sc.nextInt();
        switch (c) {
            case 1:
                addEmployee();
                break;
        }
    }

    public static void addEmployee() {
        System.out.print("""
                [1] Salaried
                [2] Hourly
                Choice: """);
        int c = sc.nextInt();
        sc.nextLine();
        switch (c) {
            case 1:
                System.out.print("Set salary: ");
                double salary = sc.nextDouble();
                sc.nextLine();
                System.out.print("Set bonus: ");
                double bonus = sc.nextDouble();
                sc.nextLine();
                System.out.print("Set name: ");
                String name = sc.nextLine();
                System.out.println("Set ID: ");
                String id = sc.nextLine();
                Employee s = new SalariedEmployee(salary, bonus, name, id);
                employees.add(s);
                break;

            case 2:
                System.out.print("Hours worked: ");
                int hoursWorked = sc.nextInt();
                sc.nextLine();
                System.out.print("Hourly rate: ");
                double hourlyRate = sc.nextDouble();
                sc.nextLine();
                System.out.print("Set name: ");
                String ename = sc.nextLine();
                System.out.println("Set ID: ");
                String eid = sc.nextLine();
                Employee h = new HourlyEmployee(hoursWorked, hourlyRate, ename, eid);
                employees.add(h);
                break;
        }

    }

    public static void viewEmployees() {
        for (Employee e : employees) {
            System.out.printf("""
                    Name: %s
                    Earnings: %.2f
                    """, e.getName(), e.calculateEarnings());
        }
    }

    public static void loadData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/employees.json"))) {
            RuntimeTypeAdapterFactory<Employee> adapter = RuntimeTypeAdapterFactory
                    .of(Employee.class, "type") // "type" is the field name in JSON
                    .registerSubtype(SalariedEmployee.class, EmployeeType.SALARIED.name())
                    .registerSubtype(HourlyEmployee.class, EmployeeType.HOURLY.name());

            Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}