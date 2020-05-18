package org.cloud.ssm.common.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;

    public CustomRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码 --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro
     * 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        LOGGER.debug("从数据库获取认证信息");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 根据用户名查询数据库
        String username = token.getUsername();
        User user = userService.findByUsername(username);

        // 账号不存在
        if (user == null) {
            LOGGER.info("Login failed, because The username or password is incorrect.");
            return null;
        }

        if (0 == user.getStatus()) {
            LOGGER.info("Account has been disabled explicitly due to being locked");
            return null;
        }

        // 认证缓存信息
        return new SimpleAuthenticationInfo(user, user.getPassword().toCharArray(),
                ShiroByteSource.of(user.getUsername()), getName());
    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOGGER.debug("从数据库获取权限信息");
        User user = (User) principals.getPrimaryPrincipal();
        String username = user.getUsername();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoles(username));
        authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    /**
     * 清除用户缓存
     * 
     * @param loginName
     */
    public void removeUserCache(String loginName) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(loginName, super.getName());
        super.clearCachedAuthenticationInfo(principals);
    }
}
