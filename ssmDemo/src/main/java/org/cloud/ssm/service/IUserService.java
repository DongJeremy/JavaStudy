package org.cloud.ssm.service;

import java.util.List;
import java.util.Set;

import org.cloud.ssm.common.base.BaseService;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.entity.vo.UserOnline;

public interface IUserService extends BaseService<User> {

    /**
     * 修改密码
     * 
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);

    /**
     * 添加用户-角色关系
     * 
     * @param userId
     * @param roleIds
     */
    void correlationRoles(Long userId, Long... roleIds);

    /**
     * 移除用户-角色关系
     * 
     * @param userId
     * @param roleIds
     */
    void uncorrelationRoles(Long userId, Long... roleIds);

    /**
     * 根据用户名查找用户
     * 
     * @param username
     * @return
     */
    User findByUsername(String username);

    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * 
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

    List<UserOnline> getOnlineUsers();

    List<UserOnline> getOnlineUsers(int pageNum, int pageSize);

    Integer getOnlineUserCount();

    void deleteUserRoles(Long uid);

    void forceLogout(String sessionId);

    boolean disableUserByID(Long id);

    boolean enableUserByID(Long id);

    boolean updatePasswordByUserId(Long id, String password0, String password1);

    void updateUserInfoByPrimaryKey(User user);

    User findUserInfoByUsername(String username);
}
