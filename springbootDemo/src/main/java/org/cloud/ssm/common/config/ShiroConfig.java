package org.cloud.ssm.common.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.cloud.ssm.common.security.ShiroDbRealm;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    /**
     * 注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
     * 
     * @return
     */
    @Bean(name = "shiroDbRealm")
    public ShiroDbRealm shiroDbRealm() {
        DefaultHashService defaultHashService = new DefaultHashService();

        // 默认算法 SHA-512
        defaultHashService.setHashAlgorithmName("SHA-512");

        // 生成 Hash 值的迭代次数
        defaultHashService.setHashIterations(500000);

        // 是否生成公盐
        defaultHashService.setGeneratePublicSalt(true);

        DefaultPasswordService defaultPasswordService = new DefaultPasswordService();
        defaultPasswordService.setHashService(defaultHashService);

        PasswordMatcher credentialsMatcher = new PasswordMatcher();
        credentialsMatcher.setPasswordService(defaultPasswordService);

        ShiroDbRealm shiroDbRealm = new ShiroDbRealm(redisCacheManager(), credentialsMatcher);

        // 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        shiroDbRealm.setAuthenticationCachingEnabled(true);

        // 缓存AuthenticationInfo信息的缓存名称
        shiroDbRealm.setAuthenticationCacheName("authenticationCache");

        // 缓存AuthorizationInfo信息的缓存名称
        shiroDbRealm.setAuthorizationCacheName("authorizationCache");
        return shiroDbRealm;
    }

    /**
     * shiroFilter
     * 
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/unauth");
        Map<String, String> filterMap = new HashMap<String, String>();
        filterMap.put("/static/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/admin/login", "anon");
        filterMap.put("/admin/**", "authc");// 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证
     * 
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 设置realm.
        securityManager.setRealm(shiroDbRealm());
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        SimpleCookie simpleCookie = new SimpleCookie("shiro.sesssion");
        simpleCookie.setPath("/");
        defaultWebSessionManager.setSessionIdCookie(simpleCookie);
        return defaultWebSessionManager;
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(600);
        redisCacheManager.setPrincipalIdFieldName("id");
        return redisCacheManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(1800);
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionInMemoryEnabled(false);
        return redisSessionDAO;
    }

    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost + ":" + redisPort);
        return redisManager;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
