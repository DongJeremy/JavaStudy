package org.cloud.ssm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.common.utils.IPUtils;
import org.cloud.ssm.common.utils.PasswordUtils;
import org.cloud.ssm.entity.Role;
import org.cloud.ssm.entity.RolePermission;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.entity.UserRole;
import org.cloud.ssm.entity.vo.UserOnline;
import org.cloud.ssm.mapper.RoleMapper;
import org.cloud.ssm.mapper.UserMapper;
import org.cloud.ssm.service.IUserService;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private SessionDAO sessionDAO;

    @Override
    public void changePassword(Long userId, String newPassword) {
        User u = userMapper.selectByPrimaryKey(userId);
        u.setPassword(newPassword);
        userMapper.updateByPrimaryKey(u);
    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        for (Long roleid : roleIds) {
            userMapper.addUserRole(userId, roleid);
        }
    }

    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        for (Long roleid : roleIds) {
            userMapper.deleteUserRole(userId, roleid);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        Set<String> roles = new HashSet<>();
        User u = userMapper.selectUserByUsername(username);
        List<UserRole> user_roles = u.getUserRoles();
        for (UserRole ur : user_roles) {
            Role r = ur.getRole();
            roles.add(r.getRole());
        }
        return roles;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<>();
        User u = userMapper.selectUserByUsername(username);
        List<UserRole> userRoles = u.getUserRoles();
        for (UserRole ur : userRoles) {
            Role role = roleMapper.selectByPrimaryKey(ur.getRole().getId());
            List<RolePermission> rolePermissions = role.getRolePermissions();
            for (RolePermission rolePermission : rolePermissions) {
                String permission = rolePermission.getPermission().getPermission();
                permissions.add(permission);
            }
        }
        return permissions;
    }

    @Override
    public List<UserOnline> getOnlineUsers() {
        List<UserOnline> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            UserOnline userOnline = new UserOnline();
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                User user = (User) principalCollection.getPrimaryPrincipal();
                userOnline.setUsername(user.getUsername());
                userOnline.setUserId(user.getId());
            }
            userOnline.setId((String) session.getId());
            userOnline.setIp(IPUtils.getIpAddr());
            userOnline.setStartTimestamp(session.getStartTimestamp());
            userOnline.setLastAccessTime(session.getLastAccessTime());
            long timeout = session.getTimeout();
            if (timeout == 0L) {
                userOnline.setStatus("离线");
            } else {
                userOnline.setStatus("在线");
            }
            userOnline.setTimeout(timeout);
            list.add(userOnline);
        }
        return list;
    }

    @Override
    public List<UserOnline> getOnlineUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return getOnlineUsers();
    }
    
    @Override
    public Integer getOnlineUserCount() {
        return getOnlineUsers().size();
    }

    @Override
    public void deleteUserRoles(Long uid) {
        userMapper.deleteUserRoles(uid);
    }

    @Override
    public void forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        if (session != null) {
            session.stop();
            session.stop();
            sessionDAO.delete(session);
        }
    }

    @Override
    public boolean disableUserByID(Long id) {
        return userMapper.updateStatusByPrimaryKey(id, 0) == 1;
    }

    @Override
    public boolean enableUserByID(Long id) {
        return userMapper.updateStatusByPrimaryKey(id, 1) == 1;
    }

    @Override
    public boolean updatePasswordByUserId(Long id, String password0, String password1) {
        User u = userMapper.selectByPrimaryKey(id);
        if (u==null) return false;
        boolean passMatch = PasswordUtils.passwordsMatch(password0, u.getPassword());
        if (!passMatch) {
            return false;
        }
        String encryptPassword = PasswordUtils.generatePassword(password1);
        userMapper.updatePasswordByUserId(id, encryptPassword);
        return true;
    }

    @Override
    public void updateUserInfoByPrimaryKey(User user) {
        userMapper.updateUserInfoByPrimaryKey(user);
    }

    @Override
    public User findUserInfoByUsername(String username) {
        return userMapper.selectUserInfoByUsername(username);
    }

}
