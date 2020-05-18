package org.cloud.ssm.mapper;

import java.util.List;

import org.cloud.ssm.entity.Role;
import org.cloud.ssm.entity.User;

public interface UserMapper {
    User findUserById(Long id);

    User findUserByPhone(String Phone);

    List<Role> findUserRole(Long id);

    User findUserByUserName(String username);
}
