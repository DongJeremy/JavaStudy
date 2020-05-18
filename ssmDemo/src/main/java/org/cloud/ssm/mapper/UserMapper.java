package org.cloud.ssm.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User selectUserByUsername(String username);

    void deleteUserRole(long userid, long roleid);

    void addUserRole(long userid, long roleid);

    void deleteUserRoles(long uid);

    int updateStatusByPrimaryKey(@Param("id") Long id, @Param("status") int status);

    void updatePasswordByUserId(@Param("id") Long id, @Param("password") String encryptPassword);

    User selectUserInfoByUsername(@Param("username") String username);

    void updateUserInfoByPrimaryKey(User user);
}
