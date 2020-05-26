package org.cloud.ssm.common.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.cloud.ssm.common.base.BaseServiceImpl;
import org.cloud.ssm.common.domain.SysUser;
import org.cloud.ssm.common.domain.SysUser.Status;
import org.cloud.ssm.common.dto.UserDto;
import org.cloud.ssm.common.mapper.UserMapper;
import org.cloud.ssm.common.service.UserService;
import org.cloud.ssm.system.model.vo.UserOnline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, SysUser> implements UserService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
    public Long getCount(SysUser entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<SysUser> getById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SysUser> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SysUser> getAll(int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long save(SysUser entity) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long deleteById(Long id) {
        // TODO Auto-generated method stub
        return 0;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserOnline> getOnlineUsers(int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getOnlineUserCount() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteUserRoles(Long uid) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void forceLogout(String sessionId) {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void updateUserInfoByPrimaryKey(SysUser user) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public SysUser findUserInfoByUsername(String username) {
        return userMapper.selectUserInfoByUsername(username);
    }

}
