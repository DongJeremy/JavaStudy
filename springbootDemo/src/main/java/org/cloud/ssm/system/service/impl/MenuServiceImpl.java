package org.cloud.ssm.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.system.mapper.MenuMapper;
import org.cloud.ssm.system.model.Menu;
import org.cloud.ssm.system.service.IMenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getTreeData(int level) {
        return menuMapper.getMenuList(level);
    }

}
