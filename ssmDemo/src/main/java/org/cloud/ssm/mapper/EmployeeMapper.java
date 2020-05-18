package org.cloud.ssm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.entity.Employee;

public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> selectAllByCondition(@Param("name") String name, @Param("departmentName") String departmentName);

    Long selectCountByCondition(@Param("name") String name, @Param("departmentName") String departmentName);
}
