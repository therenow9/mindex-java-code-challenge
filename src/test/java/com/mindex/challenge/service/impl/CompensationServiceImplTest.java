package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        compensationUrl = "http://localhost:" + port + "/compensation";
        // create url
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        // read url
    }

    @Test
    public void testCreateReadUpdate() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("z1c4543d-16bd-4663-8e08-638a9d18b22c");
        testEmployee.setFirstName("John");
        testEmployee.setFirstName("Doe");
        testEmployee.setPosition("Recruiter");
        testEmployee.setDepartment("HR");
        Compensation testCompensation = new Compensation();
        testCompensation.setEffectiveDate("09/04/2009");
        testCompensation.setSalary(82000);

        // create test employee and give their Report structure the same ID
        testEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        testCompensation.setEmployeeId(testCompensation.getEmployeeId());

        // Create checks
        Compensation createdCompensation = restTemplate
                .postForEntity(compensationUrl, testCompensation, Compensation.class, testEmployee.getEmployeeId())
                .getBody();

        assertNotNull(createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate
                .getForEntity(compensationUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployeeId(), readCompensation.getEmployeeId());

        assertNotNull(readCompensation);
        assertCompensationEquivalence(testCompensation, readCompensation);
        // had to setup specifc constructors to pass tests , no idea why
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation = restTemplate.exchange(compensationIdUrl,
                HttpMethod.PUT,
                new HttpEntity<Compensation>(readCompensation, headers),
                Compensation.class,
                readCompensation.getEmployeeId()).getBody();

        assertCompensationEquivalence(readCompensation, updatedCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
