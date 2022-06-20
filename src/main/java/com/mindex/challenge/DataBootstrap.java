package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;

@Component
public class DataBootstrap {
    private static final String DATASTORE_LOCATION_1 = "/static/employee_database.json";
    private static final String DATASTORE_LOCATION_2 = "/static/compensation_database.json";
    // add second databse for compensation as well

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {

        InputStream inpStr1 = this.getClass().getResourceAsStream(DATASTORE_LOCATION_1);
        BufferedInputStream inputStream1 = new BufferedInputStream(inpStr1);
        InputStream inpStr2 = this.getClass().getResourceAsStream(DATASTORE_LOCATION_2);
        BufferedInputStream inputStream2 = new BufferedInputStream(inpStr2);
        // Use buffered streams to read from both JSON files
        Employee[] employees = null;
        Compensation[] compensations = null;

        try {
            employees = objectMapper.readValue(inputStream1, Employee[].class);
            compensations = objectMapper.readValue(inputStream2, Compensation[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }

        for (Compensation compensation : compensations) {
            compensationRepository.insert(compensation);
        }
    }
}