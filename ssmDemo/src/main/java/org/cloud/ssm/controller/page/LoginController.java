package org.cloud.ssm.controller.page;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.cloud.ssm.common.annotation.CsrfToken;
import org.cloud.ssm.common.bean.ProjectProperties;
import org.cloud.ssm.common.bean.ResultBean;
import org.cloud.ssm.common.utils.CaptchaUtil;
import org.cloud.ssm.common.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * GET 登录
     * 
     * @return
     */
    @GetMapping("/login")
    @CsrfToken(create = true)
    public String login(Model model) {
        logger.debug("GET请求登录");
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        model.addAttribute("isCaptcha", properties.isCaptchaOpen());
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/admin";
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
            String captcha, @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
        logger.debug("POST请求登录");
        // 改为全部抛出异常，避免ajax csrf token被刷新

        // 判断账号密码是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultBean.error("账号或密码为空！");
        }

        // 判断验证码是否正确
        ProjectProperties properties = SpringContextUtil.getBean(ProjectProperties.class);
        if (properties.isCaptchaOpen()) {
            Session session = SecurityUtils.getSubject().getSession();
            String sessionCaptcha = (String) session.getAttribute("captcha");
            if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(sessionCaptcha)
                    || !captcha.toUpperCase().equals(sessionCaptcha.toUpperCase())) {
                return ResultBean.error("验证码错误");
            }
            session.removeAttribute("captcha");
        }

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
     * 验证码图片
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应头信息，通知浏览器不要缓存
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "-1");
        response.setContentType("image/jpeg");

        // 获取验证码
        String code = CaptchaUtil.getRandomCode();
        // 将验证码输入到session中，用来验证
        request.getSession().setAttribute("captcha", code);
        // 输出到web页面
        ImageIO.write(CaptchaUtil.genCaptcha(code), "jpg", response.getOutputStream());
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
