package org.cloud.ssm.common.service;

import org.cloud.ssm.common.domain.SysUser;
import org.cloud.ssm.common.dto.UserDto;

public interface UserService {

    SysUser saveUser(UserDto userDto);

    SysUser updateUser(UserDto userDto);

    SysUser getUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);

}
