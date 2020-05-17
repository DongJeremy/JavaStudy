package org.cloud.ssm.controller;

import java.util.List;

import org.cloud.ssm.entity.Employee;
import org.cloud.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest/employee")
public class EmployeeController {

    private static final String SUCCESS = "success";

    @Autowired
    private EmployeeService service;

    @GetMapping("/listEmployee")
    public @ResponseBody List<Employee> listEmployee() {
        List<Employee> listEmployee = service.listEmployee();
        return listEmployee;
    }

    @PostMapping("/addEmployee")
    public @ResponseBody String addEmployee(Employee employee) {
        service.addEmployee(employee);
        return SUCCESS;
    }

    @PostMapping("/deleteEmployee")
    public @ResponseBody String deleteEmployee(Employee employee) {
        service.deleteEmployee(employee.getId());
        return SUCCESS;
    }

    @GetMapping("/getEmployee")
    public @ResponseBody Employee getEmployee(Employee employee) {
        return service.getEmployee(employee.getId());
    }

    @PostMapping("/updateEmployee")
    public @ResponseBody String updateEmployee(Employee employee) {
        service.updateEmployee(employee);
        return SUCCESS;
    }

}
