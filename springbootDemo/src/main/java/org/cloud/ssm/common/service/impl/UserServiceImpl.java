package org.cloud.ssm.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.common.domain.SysUser;
import org.cloud.ssm.common.domain.SysUser.Status;
import org.cloud.ssm.common.dto.LoginUser;
import org.cloud.ssm.common.dto.UserDto;
import org.cloud.ssm.common.mapper.UserMapper;
import org.cloud.ssm.common.service.UserService;
import org.cloud.ssm.common.util.EncryptPasswordUtil;
import org.cloud.ssm.common.util.IPUtils;
import org.cloud.ssm.system.model.vo.UserOnline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, SysUser> implements UserService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    @Transactional
    public SysUser saveUser(UserDto userDto) {
        SysUser user = userDto;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.VALID);
        userMapper.insert(user);
        saveUserRoles(user.getId(), userDto.getRoleIds());

        log.debug("新增用户{}", user.getUsername());
        return user;
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds != null) {
            userMapper.deleteUserRole(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                userMapper.saveUserRoles(userId, roleIds);
            }
        }
    }

    @Override
    public SysUser getUser(String username) {
        return userMapper.selectByUser(username);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        SysUser u = userMapper.selectByUser(username);
        if (u == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }
        userMapper.changePassword(u.getId(), passwordEncoder.encode(newPassword));
        log.debug("修改{}的密码", username);
    }

    @Override
    @Transactional
    public SysUser updateUser(UserDto userDto) {
        userMapper.updateByPrimaryKey(userDto);
        saveUserRoles(userDto.getId(), userDto.getRoleIds());
        return userDto;
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        // TODO Auto-generated method stub

    }

    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        // TODO Auto-generated method stub

    }

    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        // TODO Auto-generated method stub

    }

    @Override
    public SysUser findByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> findRoles(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> findPermissions(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserOnline> getOnlineUsers() {
        List<UserOnline> list = new ArrayList<>();
        List<Object> principals = sessionRegistry.getAllPrincipals();
        for (Object principal : principals) {
            for (SessionInformation sess : sessionRegistry.getAllSessions(principal, false)) {
                UserOnline userOnline = new UserOnline();
                LoginUser user = (LoginUser) sess.getPrincipal();
                userOnline.setUsername(user.getUsername());
                userOnline.setStartTimestamp(new Date(user.getLoginTime()));
                userOnline.setLastAccessTime(sess.getLastRequest());
                if (!sess.isExpired()) {
                    userOnline.setStatus("在线");
                } else {
                    userOnline.setStatus("离线");
                }
                userOnline.setId((String) sess.getSessionId());
                userOnline.setIp(IPUtils.getIpAddr());
                list.add(userOnline);
            }
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
        // TODO Auto-generated method stub

    }

    @Override
    public void forceLogout(String sessionId) {
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object principal : users) {
            if (principal instanceof LoginUser) {
                List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                if (null != sessionsInfo && sessionsInfo.size() > 0) {
                    for (SessionInformation sessionInformation : sessionsInfo) {
                        if (sessionId.equals(sessionInformation.getSessionId())) {
                            sessionInformation.expireNow();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean disableUserByID(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean enableUserByID(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updatePasswordByUserId(Long id, String password0, String password1) {
        SysUser u = userMapper.selectByPrimaryKey(id);
        if (u == null)
            return false;
        boolean passMatch = EncryptPasswordUtil.passwordsMatch(password0, u.getPassword());
        if (!passMatch) {
            return false;
        }
        String encryptPassword = EncryptPasswordUtil.encrypt(password1);
        userMapper.updatePasswordByUserId(id, encryptPassword);
        return true;
    }

    @Override
    public void updateUserInfoByPrimaryKey(SysUser user) {
        userMapper.updateUserInfoByPrimaryKey(user);
    }

    @Override
    public SysUser findUserInfoByUsername(String username) {
        return userMapper.selectUserInfoByUsername(username);
    }

}
