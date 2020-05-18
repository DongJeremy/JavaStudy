package org.cloud.ssm.service;

import java.util.List;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.entity.Menu;

public interface IMenuService extends BaseService<Menu> {
    List<Menu> getTreeData(int level);
}
