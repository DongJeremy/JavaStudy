package org.cloud.ssm.common.service.impl;

import java.util.List;

import org.cloud.ssm.common.domain.Role;
import org.cloud.ssm.common.domain.SysUser;
import org.cloud.ssm.common.domain.SysUser.Status;
import org.cloud.ssm.common.dto.LoginUser;
import org.cloud.ssm.common.mapper.RoleMapper;
import org.cloud.ssm.common.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUser(username);

        if (sysUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        } else if (sysUser.getStatus() == Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (sysUser.getStatus() == Status.DISABLED) {
            throw new DisabledException("用户已作废");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        List<Role> roles = roleMapper.listByUserId(sysUser.getId());
        loginUser.setRoles(roles);

        return loginUser;
    }

}
