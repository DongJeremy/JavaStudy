package org.cloud.ssm.system.service.impl;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.system.mapper.DepartmentMapper;
import org.cloud.ssm.system.model.Department;
import org.cloud.ssm.system.service.IDepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
