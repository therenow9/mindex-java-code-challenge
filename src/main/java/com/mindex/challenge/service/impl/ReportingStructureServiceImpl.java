package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Creating reporting structure for employee [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        // error check for employee
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        ReportingStructure reportingStructure = new ReportingStructure(employee);
        int numberOfReports = 0;
        if (employee.getDirectReports() != null) {
            // If employee has some direct reports
            List<Employee> directReports = employee.getDirectReports();
            numberOfReports = directReports.size();
            if (numberOfReports != 0) {
                for (int i = 0; i < numberOfReports; i++) {
                    directReports.set(i, employeeRepository.findByEmployeeId(directReports.get(i).getEmployeeId()));
                }
            }
            // get direct reports of the direct report
            for (Employee subordinate : directReports) {
                if (subordinate.getDirectReports() != null) {
                    List<Employee> Sub_Subordinates = subordinate.getDirectReports();
                    for (int j = 0; j < Sub_Subordinates.size(); j++) {
                        Sub_Subordinates.set(j,
                                employeeRepository.findByEmployeeId(Sub_Subordinates.get(j).getEmployeeId()));
                    }
                    numberOfReports += subordinate.getDirectReports().size();
                }
            }
        }
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }
}
