package org.cloud.ssm.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.system.mapper.PermissionMapper;
import org.cloud.ssm.system.model.Permission;
import org.cloud.ssm.system.service.IPermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPagePermissions(int pageNum, int pageSize) {
        return this.getAll(pageNum, pageSize);
    }

}
