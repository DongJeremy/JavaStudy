package org.cloud.ssm.common.service;

import org.cloud.ssm.common.domain.Permission;

public interface PermissionService {

    void save(Permission permission);

    void update(Permission permission);

    void delete(Long id);
}
