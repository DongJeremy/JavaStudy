package org.cloud.ssm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.entity.Menu;
import org.cloud.ssm.mapper.MenuMapper;
import org.cloud.ssm.service.IMenuService;
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
