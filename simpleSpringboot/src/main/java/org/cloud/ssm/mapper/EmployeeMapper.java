package org.cloud.ssm.mapper;

import java.util.List;

import org.cloud.ssm.entity.Employee;

public interface EmployeeMapper {
    void addEmployee(Employee employee);

    List<Employee> listEmployee();

    void deleteEmployee(long employeeID);

    void updateEmployee(Employee employee);

    Employee getEmployee(long employeeID);
}
