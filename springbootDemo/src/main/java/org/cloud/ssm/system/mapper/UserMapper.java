package org.cloud.ssm.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.ssm.common.base.BaseMapper;
import org.cloud.ssm.system.model.User;

public interface UserMapper extends BaseMapper<User> {

    User selectUserByUsername(String username);

    void deleteUserRole(long userid, long roleid);

    void addUserRole(long userid, long roleid);

    void deleteUserRoles(long uid);

    int updateStatusByPrimaryKey(@Param("id") Long id, @Param("status") int status);

    void updatePasswordByUserId(@Param("id") Long id, @Param("password") String encryptPassword);

}
