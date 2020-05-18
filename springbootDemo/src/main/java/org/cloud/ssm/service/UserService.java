package org.cloud.ssm.service;

import java.util.List;
import java.util.Map;

import org.cloud.ssm.entity.Role;
import org.cloud.ssm.entity.User;

public interface UserService {
    User findUserByPhone(String phone);

    List<Role> findUserRole(Long id);

    Map<String, Object> findUserByUserName(String username, String password);
}
