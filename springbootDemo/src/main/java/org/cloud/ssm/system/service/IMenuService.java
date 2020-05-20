package org.cloud.ssm.system.service;

import java.util.List;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.system.model.Menu;

public interface IMenuService extends BaseService<Menu> {
    public List<Menu> getTreeData(int level);
}
