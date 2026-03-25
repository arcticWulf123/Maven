package com.activity11.model;

public class SalariedEmployee extends Employee {
    private double baseSalary;
    private double bonus;

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public SalariedEmployee(double baseSalary, double bonus, String name, String employeeID) {
        super(name, employeeID, EmployeeType.SALARIED);
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    @Override
    public double calculateEarnings() {
        return baseSalary + bonus;
    }

}
