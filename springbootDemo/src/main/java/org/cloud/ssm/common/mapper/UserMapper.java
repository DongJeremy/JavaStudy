package org.cloud.ssm.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.common.domain.SysUser;

@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

    SysUser selectByUser(@Param("username") String username);

    int changePassword(@Param("id") Long id, @Param("password") String password);

    Integer count(@Param("params") Map<String, Object> params);

    List<SysUser> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
            @Param("limit") Integer limit);

    int deleteUserRole(@Param("userId") Long userId);

    int saveUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
