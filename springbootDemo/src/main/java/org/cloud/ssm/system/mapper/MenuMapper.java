package org.cloud.ssm.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.system.model.Menu;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenuList(@Param("level") int level);
}
