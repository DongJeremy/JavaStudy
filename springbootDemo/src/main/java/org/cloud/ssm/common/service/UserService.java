package org.cloud.ssm.common.service;

import java.util.List;
import java.util.Set;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.common.domain.SysUser;
import org.cloud.ssm.common.dto.UserDto;
import org.cloud.ssm.system.model.vo.UserOnline;

public interface UserService extends BaseService<SysUser>{

    SysUser saveUser(UserDto userDto);

    SysUser updateUser(UserDto userDto);

    SysUser getUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);
    
    public void changePassword(Long userId, String newPassword);

    public void correlationRoles(Long userId, Long... roleIds);

    public void uncorrelationRoles(Long userId, Long... roleIds);

    public SysUser findByUsername(String username);

    public Set<String> findRoles(String username);

    public Set<String> findPermissions(String username);

    public List<UserOnline> getOnlineUsers();

    public List<UserOnline> getOnlineUsers(int pageNum, int pageSize);

    public Integer getOnlineUserCount();

    public void deleteUserRoles(Long uid);

    public void forceLogout(String sessionId);

    public boolean disableUserByID(Long id);

    public boolean enableUserByID(Long id);

    public boolean updatePasswordByUserId(Long id, String password0, String password1);

    void updateUserInfoByPrimaryKey(SysUser user);

    SysUser findUserInfoByUsername(String username);

}
