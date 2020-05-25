package org.cloud.ssm.common.service;

import org.cloud.ssm.common.dto.RoleDto;

public interface RoleService {

    void saveRole(RoleDto roleDto);

    void deleteRole(Long id);
}
