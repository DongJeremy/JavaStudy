package org.cloud.ssm.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.system.model.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Employee> selectAllByCondition(@Param("name") String name, @Param("departmentName") String departmentName);

    Long selectCountByCondition(@Param("name") String name, @Param("departmentName") String departmentName);
}
