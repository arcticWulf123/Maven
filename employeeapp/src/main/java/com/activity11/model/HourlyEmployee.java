package com.activity11.model;

public class HourlyEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    public HourlyEmployee(int hoursWorked, double hourlyRate, String name, String employeeId) {
        super(name, employeeId, EmployeeType.HOURLY);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    public double calculateEarnings() {
        return hoursWorked * hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
