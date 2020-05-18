package org.cloud.ssm.controller.api;

import java.util.List;

import org.cloud.ssm.common.bean.PageResultBean;
import org.cloud.ssm.common.bean.ResultBean;
import org.cloud.ssm.entity.Employee;
import org.cloud.ssm.entity.vo.EmployeeVO;
import org.cloud.ssm.service.IEmployeeService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Mapper mapper ;

    @Autowired
    private IEmployeeService service;

    @GetMapping("/list")
    public @ResponseBody PageResultBean<Employee> listEmployee(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Employee> emps = null;
        long count = 0;
        if ((department == null) && (username == null)) {
            emps = service.getAll(page, limit);
            count = service.getCount(new Employee());
        } else {
            emps = service.getAllByCondition(username, department, page, limit);
            count = service.getCountByCondition(username, department);
        }
        return new PageResultBean<Employee>(count, emps);
    }

    @PostMapping
    public @ResponseBody ResultBean addEmployee(@RequestBody EmployeeVO employeeVo) {
        Employee employee = mapper.map(employeeVo, Employee.class);
        long effectRow = service.save(employee);
        if (effectRow > 0) {
            logger.info("add employee successful.");
            return ResultBean.success();
        }
        return ResultBean.error("add employee fail.");
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResultBean deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
        logger.info("delete employee successful.");
        return ResultBean.success();
    }

    @GetMapping("/get")
    public @ResponseBody Employee getEmployee(Employee employee) {
        return service.getById(employee.getId()).orElse(new Employee());
    }

    @PutMapping
    public @ResponseBody ResultBean updateEmployee(@RequestBody EmployeeVO employeeVo) {
        Employee employee = mapper.map(employeeVo, Employee.class);
        long effectRow = service.save(employee);
        if (effectRow > 0) {
            logger.info("update employee successful.");
            return ResultBean.success();
        }
        return ResultBean.error("update employee fail.");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultBean removeEmp(@RequestBody List<String> ids) {
        for (String id : ids) {
            service.deleteById(Long.parseLong(id));
        }
        return ResultBean.success();
    }

}
