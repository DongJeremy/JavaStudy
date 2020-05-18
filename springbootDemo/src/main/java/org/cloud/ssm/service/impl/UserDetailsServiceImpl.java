package org.cloud.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cloud.ssm.entity.Role;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户密码错误");
        }
        List<Role> userRole = userMapper.findUserRole(user.getId());
        List<SimpleGrantedAuthority> authoritylist = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRole)) {
            for (Role role : userRole) {
                authoritylist.add(new SimpleGrantedAuthority(role.getRoleCard()));
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authoritylist);
        return userDetails;
    }

}
