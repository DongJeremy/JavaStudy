package org.cloud.ssm.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cloud.ssm.common.annotation.CsrfToken;
import org.cloud.ssm.common.base.BaseController;
import org.cloud.ssm.common.util.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

    /**
     * GET 登录
     * @return
     */
    @GetMapping("/login")
    @CsrfToken(create = true)
    public String login() {
        logger.debug("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/main";
        }
        return "login";
    }

    /**
     * POST 登录 shiro 写法
     * 
     * @param username 用户名
     * @param password 密码
     * @return {Object}
     */
    @PostMapping("/login")
    @CsrfToken(remove = true)
    @ResponseBody
    public Object loginPost(HttpServletRequest request, HttpServletResponse response, String username, String password,
            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
        logger.debug("POST请求登录");
        // 改为全部抛出异常，避免ajax csrf token被刷新
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        
        // 设置记住密码
        token.setRememberMe(1 == rememberMe);
        try {
            user.login(token);
            logger.info("User: " + username + " login successful!");
            return ResultBean.success("登录成功");
        } catch (UnknownAccountException e) {
            logger.info("Cannot find the user " + username + ", because it does not exist");
        } catch (DisabledAccountException e) {
            logger.info(username + " cannot login, because account has been disabled for some reason.");
        } catch (IncorrectCredentialsException e) {
            logger.info(username + " log on failed. Ensure the user name and password are correct.");
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        return ResultBean.error("账号或密码错误！");
    }

    /**
     * 未授权
     * 
     * @return {String}
     */
    @GetMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "unauth";
    }

    /**
     * 退出
     * 
     * @return {Result}
     */
    @PostMapping("/logout")
    @ResponseBody
    public Object logout() {
        logger.debug("登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultBean.success();
    }
}
