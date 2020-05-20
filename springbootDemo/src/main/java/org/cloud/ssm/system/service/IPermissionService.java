package org.cloud.ssm.system.service;

import java.util.List;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.system.model.Permission;

public interface IPermissionService extends BaseService<Permission> {
    public List<Permission> getPagePermissions(int pagenum, int pagesize);
}
