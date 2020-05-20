package org.cloud.ssm.system.service.impl;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.system.mapper.DeptMapper;
import org.cloud.ssm.system.model.Dept;
import org.cloud.ssm.system.service.IDeptService;
import org.springframework.stereotype.Service;

@Service("deptService")
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements IDeptService {

}
