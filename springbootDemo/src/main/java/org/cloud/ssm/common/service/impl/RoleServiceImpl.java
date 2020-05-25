package org.cloud.ssm.common.service.impl;

import java.util.List;

import org.cloud.ssm.common.domain.Role;
import org.cloud.ssm.common.dto.RoleDto;
import org.cloud.ssm.common.mapper.RoleMapper;
import org.cloud.ssm.common.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public void saveRole(RoleDto roleDto) {
        Role role = roleDto;
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);

        if (role.getId() != null) {// 修改
            updateRole(role, permissionIds);
        } else {// 新增
            saveRole(role, permissionIds);
        }
    }

    private void saveRole(Role role, List<Long> permissionIds) {
        Role r = roleMapper.getRole(role.getName());
        if (r != null) {
            throw new IllegalArgumentException(role.getName() + "已存在");
        }

        roleMapper.save(role);
        if (!CollectionUtils.isEmpty(permissionIds)) {
            roleMapper.saveRolePermission(role.getId(), permissionIds);
        }
        log.debug("新增角色{}", role.getName());
    }

    private void updateRole(Role role, List<Long> permissionIds) {
        Role r = roleMapper.getRole(role.getName());
        if (r != null && r.getId() != role.getId()) {
            throw new IllegalArgumentException(role.getName() + "已存在");
        }

        roleMapper.update(role);

        roleMapper.deleteRolePermission(role.getId());
        if (!CollectionUtils.isEmpty(permissionIds)) {
            roleMapper.saveRolePermission(role.getId(), permissionIds);
        }
        log.debug("修改角色{}", role.getName());
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        roleMapper.deleteRolePermission(id);
        roleMapper.deleteRoleUser(id);
        roleMapper.delete(id);

        log.debug("删除角色id:{}", id);
    }

}
