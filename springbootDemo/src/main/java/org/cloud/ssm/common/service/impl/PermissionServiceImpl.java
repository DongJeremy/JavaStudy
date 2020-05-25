package org.cloud.ssm.common.service.impl;

import org.cloud.ssm.common.domain.Permission;
import org.cloud.ssm.common.mapper.PermissionMapper;
import org.cloud.ssm.common.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void save(Permission permission) {
        permissionMapper.save(permission);

        log.debug("新增菜单{}", permission.getName());
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.update(permission);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        permissionMapper.deleteRolePermission(id);
        permissionMapper.delete(id);
        permissionMapper.deleteByParentId(id);

        log.debug("删除菜单id:{}", id);
    }

}
