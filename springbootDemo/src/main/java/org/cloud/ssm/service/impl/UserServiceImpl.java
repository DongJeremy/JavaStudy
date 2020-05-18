package org.cloud.ssm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.ssm.entity.Role;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.mapper.UserMapper;
import org.cloud.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserServiceImpl implements UserService {

    private String secret = "ZHI_MA_KAI_MEN";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User findUserByPhone(String phone) {
        User user = userMapper.findUserByPhone(phone);
        return user;
    }

    @Override
    public List<Role> findUserRole(Long id) {
        List<Role> userRole = userMapper.findUserRole(id);
        return userRole;
    }

    @Override
    public Map<String, Object> findUserByUserName(String username, String password) {
        User user = userMapper.findUserByUserName(username);
        // 4 加载UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        // 1 创建UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password);
        // 2 认证
        Authentication authenticate = authenticationManager.authenticate(token);
        // 3 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // 创建token
        Map<String, Object> maps = new HashMap<>();
        maps.put("username", username);
        String token1 = Jwts.builder().setClaims(maps)
                .setExpiration(new Date(System.currentTimeMillis() + 604800 * 1000))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
        // 返回map
        Map<String, Object> info = new HashMap<>();
        info.put("user", user);
        info.put("token", token1);
        return info;
    }
}
