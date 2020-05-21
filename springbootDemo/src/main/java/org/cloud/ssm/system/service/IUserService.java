package org.cloud.ssm.system.service;

import java.util.List;
import java.util.Set;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.system.model.User;
import org.cloud.ssm.system.vo.UserOnline;

public interface IUserService extends BaseService<User> {

    /**
     * 修改密码
     * 
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     * 
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(Long userId, Long... roleIds);

    /**
     * 移除用户-角色关系
     * 
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户名查找用户
     * 
     * @param username
     * @return
     */
    public User findByUsername(String username);

    public Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * 
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username);

    public List<UserOnline> getOnlineUsers();

    public List<UserOnline> getOnlineUsers(int pageNum, int pageSize);

    public Integer getOnlineUserCount();

    public void deleteUserRoles(Long uid);

    public void forceLogout(String sessionId);

    public boolean disableUserByID(Long id);

    public boolean enableUserByID(Long id);

    public boolean updatePasswordByUserId(Long id, String password0, String password1);

    void updateUserInfoByPrimaryKey(User user);

    User findUserInfoByUsername(String username);

}
