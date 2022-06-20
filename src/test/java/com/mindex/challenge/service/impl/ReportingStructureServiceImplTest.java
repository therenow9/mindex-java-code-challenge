package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;
    private String employeeIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;
    @LocalServerPort

    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        employeeIdUrl = "http://localhost:" + port + "/employee";

    }

    // I decided to test for 3 occurences, nested reports since I got errors with
    // this initially, non-nested reports, and zero-reports
    @Test
    public void testReportingStructureNoReports() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee zeroEmployee = new Employee();
        zeroEmployee.setFirstName("George");
        zeroEmployee.setLastName("Harrison");
        zeroEmployee.setPosition("Developer III");
        zeroEmployee.setDepartment("Engineering");
        zeroEmployee.setEmployeeId("c0c2293d-16bd-4603-8e08-638a9d18b22c");
        // set up employee (George Harrison)that we know has 0 reports (I tried to
        // import from employee
        // repository but ended up just going the long way instead)
        Employee zeroEmployeeRest = restTemplate
                .postForEntity(employeeIdUrl, zeroEmployee, Employee.class, zeroEmployee.getEmployeeId())
                .getBody();
        // Employee rest template
        ReportingStructure zeroReports = restTemplate.getForEntity(reportingStructureUrl,
                ReportingStructure.class, zeroEmployeeRest.getEmployeeId()).getBody();
        // Reporting Structure rest template
        assertEquals(0, zeroReports.getNumberOfReports());
        // value confirmed
    }

    // A bit messy yeah I know
    @Test
    public void testReportingStructureNonNestedReports() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set up employee (Ringo Starr)that we know has reports that don't nest
        Employee nonNestedEmployee = new Employee();
        nonNestedEmployee.setFirstName("Ringo");
        nonNestedEmployee.setLastName("Starr");
        nonNestedEmployee.setPosition("Developer V");
        nonNestedEmployee.setDepartment("Engineering");
        nonNestedEmployee.setEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
        List<Employee> reportsList = new ArrayList<Employee>();
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        emp1.setEmployeeId("62c1084e-6e34-4630-93fd-9153afb65309");
        emp2.setEmployeeId("c0c2293d-16bd-4603-8e08-638a9d18b22c");
        // slightly modified user id to solve errors
        Employee emp1Rest = restTemplate.postForEntity(employeeIdUrl, emp1, Employee.class, emp1.getEmployeeId())
                .getBody();
        Employee emp2Rest = restTemplate.postForEntity(employeeIdUrl, emp2, Employee.class, emp2.getEmployeeId())
                .getBody();
        reportsList.add(emp1Rest);
        reportsList.add(emp2Rest);
        // add dummy employees to direct reports
        nonNestedEmployee.setDirectReports(reportsList);
        // set up employee that we know has 0 reports (I tried to import from employee
        // repository but ended up just going the long way instead)
        Employee nonNestedEmployeeRest = restTemplate
                .postForEntity(employeeIdUrl, nonNestedEmployee, Employee.class)
                .getBody();
        // Employee rest template
        ReportingStructure nonNestedReports = restTemplate.getForEntity(reportingStructureUrl,
                ReportingStructure.class, nonNestedEmployeeRest.getEmployeeId()).getBody();
        // Reporting Structure rest template
        assertEquals(2, nonNestedReports.getNumberOfReports());
        // value confirmed
    }

    // A bit messy yeah I know
    @Test
    public void testReportingStructureNestedReports() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set up employee (Ringo Starr)that we know has reports that don't nest
        Employee nonNestedEmployee1 = new Employee();
        nonNestedEmployee1.setFirstName("Ringo");
        nonNestedEmployee1.setLastName("Starr");
        nonNestedEmployee1.setPosition("Developer V");
        nonNestedEmployee1.setDepartment("Engineering");
        nonNestedEmployee1.setEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");

        Employee nonNestedEmployee2 = new Employee();
        nonNestedEmployee2.setFirstName("Paul");
        nonNestedEmployee2.setLastName("McCartney");
        nonNestedEmployee2.setPosition("Developer ID");
        nonNestedEmployee2.setDepartment("Engineering");
        nonNestedEmployee2.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        List<Employee> reportsList = new ArrayList<Employee>();
        Employee emp1 = new Employee();
        Employee emp2 = new Employee();
        emp1.setEmployeeId("62c1084e-6e34-4630-93fd-9153afb65309");
        emp2.setEmployeeId("c0c2293d-16bd-4603-8e08-638a9d18b22c");
        // slightly modified user id to solve errors
        Employee emp1Rest = restTemplate.postForEntity(employeeIdUrl, emp1, Employee.class, emp1.getEmployeeId())
                .getBody();
        Employee emp2Rest = restTemplate.postForEntity(employeeIdUrl, emp2, Employee.class, emp2.getEmployeeId())
                .getBody();
        reportsList.add(emp1Rest);
        reportsList.add(emp2Rest);
        // add dummy employees to direct reports
        nonNestedEmployee1.setDirectReports(reportsList);
        // set up employee that we know has 0 reports (I tried to import from employee
        // repository but ended up just going the long way instead)
        Employee nonNestedEmployeeRest1 = restTemplate
                .postForEntity(employeeIdUrl, nonNestedEmployee1, Employee.class)
                .getBody();
        Employee nonNestedEmployeeRest2 = restTemplate
                .postForEntity(employeeIdUrl, nonNestedEmployee2, Employee.class)
                .getBody();
        // Employee rest template

        // Create Nested Employee
        Employee nestedEmployee = new Employee();
        nestedEmployee.setFirstName("John");
        nestedEmployee.setLastName("Lennon");
        nestedEmployee.setPosition("Development Manager");
        nestedEmployee.setDepartment("Engineering");
        nestedEmployee.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        List<Employee> nestedReportsList = new ArrayList<Employee>();
        nestedReportsList.add(nonNestedEmployeeRest1);
        nestedReportsList.add(nonNestedEmployeeRest2);
        nestedEmployee.setDirectReports(nestedReportsList);
        Employee nestedEmployeeRest = restTemplate
                .postForEntity(employeeIdUrl, nestedEmployee, Employee.class)
                .getBody();
        // Employee rest template
        ReportingStructure nestedReports = restTemplate.getForEntity(reportingStructureUrl,
                ReportingStructure.class, nestedEmployeeRest.getEmployeeId()).getBody();
        // Reporting Structure rest template
        assertEquals(4, nestedReports.getNumberOfReports());
        // value confirmed
    }
}
