package org.cloud.ssm.system.mapper;

import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.system.model.Permission;

public interface PermissionMapper extends BaseMapper<Permission> {
    void deletePermissionsById(Long permissionid);
}
