package org.cloud.ssm.service;

import java.util.List;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.entity.Permission;

public interface IPermissionService extends BaseService<Permission> {
    List<Permission> getPagePermissions(int pagenum, int pagesize);
}
