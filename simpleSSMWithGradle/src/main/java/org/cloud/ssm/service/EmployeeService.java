package org.cloud.ssm.service;

import java.util.List;

import org.cloud.ssm.entity.Employee;

public interface EmployeeService {
    void addEmployee(Employee employee);

    List<Employee> listEmployee();

    void deleteEmployee(long employeeID);

    Employee getEmployee(long employeeID);

    void updateEmployee(Employee employee);
}
