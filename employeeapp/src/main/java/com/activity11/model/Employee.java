package com.activity11.model;

public abstract class Employee {
    private String name;
    private String employeeId;
    protected EmployeeType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = type;
    }

    public Employee(String name, String employeeId, EmployeeType type) {
        this.name = name;
        this.employeeId = employeeId;
        this.type = type;
    }

    public abstract double calculateEarnings();

    @Override
    public String toString() {
        return String.format("""
                Name: %s
                ID: %s
                Type: %s
                """, name, employeeId, type);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Employee) {
            Employee otherEmployee = (Employee) o;
            return this.employeeId == otherEmployee.employeeId;
        }
        return false;
    }
}
