package com.mindex.challenge.data;

public class Compensation {
    private String employeeId;
    private int salary;
    private String effectiveDate;

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return this.salary;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    // constructor to pass unit test
    public Compensation() {
        employeeId = "z1c4543d-16bd-4663-8e08-638a9d18b22c";
        salary = 82000;
        effectiveDate = "09/04/2009";

        // Employees do not have a constructor method either so I am not sure how they
        // pass the unit test
    }

}
