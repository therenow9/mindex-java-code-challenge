//Author Jeremy Scheuerman for Mindex Coding Test
package com.mindex.challenge.data;

public class ReportingStructure {
    private int numberOfReports = 0;
    private Employee employee;

    public ReportingStructure(Employee employee) {
        this.employee = employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public int getNumberOfReports() {
        return this.numberOfReports;
    }

    public ReportingStructure() {
        // new constructor for unit test

    }

}