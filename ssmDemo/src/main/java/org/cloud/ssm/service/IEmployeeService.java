package org.cloud.ssm.service;

import java.util.List;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.entity.Employee;

public interface IEmployeeService extends BaseService<Employee> {
    List<Employee> getAllByCondition(String username, String deptName, int pageNum, int pageSize);

    Long getCountByCondition(String username, String deptName);
    
    Long batchSaveEmployee(List<Employee> list);
}
