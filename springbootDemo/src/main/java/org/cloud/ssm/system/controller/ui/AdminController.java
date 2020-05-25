package org.cloud.ssm.system.controller.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cloud.ssm.common.dto.LoginUser;
import org.cloud.ssm.common.service.UserService;
import org.cloud.ssm.common.util.UserUtil;
import org.cloud.ssm.system.info.Server;
import org.cloud.ssm.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private IMenuService service;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping(value = { "/", "/main" })
    public String index(ModelMap model) {
        model.addAttribute("menus", service.getTreeData(5));
        return "admin/index";
    }

    @GetMapping("/home")
    public String welcome(ModelMap model) {
        model.addAttribute("userCount", 2);
        model.addAttribute("roleCount", 2);
        model.addAttribute("menuCount", 12);
        model.addAttribute("loginLogCount", 51);
        model.addAttribute("sysLogCount", 478);
        model.addAttribute("userOnlineCount", 2);
        return "admin/home";
    }

    @GetMapping("/weekLoginCount")
    @ResponseBody
    public List<Integer> recentlyWeekLoginCount() {
        List<Integer> recentlyWeekLoginCount = new ArrayList<Integer>();
        recentlyWeekLoginCount.add(10);
        recentlyWeekLoginCount.add(4);
        recentlyWeekLoginCount.add(6);
        recentlyWeekLoginCount.add(9);
        recentlyWeekLoginCount.add(20);
        recentlyWeekLoginCount.add(1);
        recentlyWeekLoginCount.add(13);
        return recentlyWeekLoginCount;
    }

    @GetMapping("/systemInfo")
    public String serverInfo(ModelMap model) throws Exception {
        Server server = new Server();
        server.copyTo();
        model.addAttribute("server", server);
        return "admin/system/sysinfo-list";
    }

    @GetMapping("/syslog")
    public String syslog(ModelMap model) throws Exception {
        return "admin/system/syslog-list";
    }

    @GetMapping("/temp")
    public String temp(ModelMap model) throws Exception {
        return "admin/temp";
    }

    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/userInfo")
    public String toUserInfo(Model model) {
        LoginUser user = UserUtil.getLoginUser();
        //model.addAttribute("user", userService.findUserInfoByUsername(user.getUsername()));
        return "admin/user/user-info";
    }

    @GetMapping("/editpass")
    public String editPassword(ModelMap model) {
        LoginUser user = UserUtil.getLoginUser();
        model.addAttribute("id", user.getId());
        return "admin/user/edit-passwd";
    }

    @GetMapping("/userOnline")
    public String userOnline(ModelMap model) throws Exception {
        return "admin/user/user-online-list";
    }

    @GetMapping("/userView")
    public String userView(ModelMap model) throws Exception {
        return "admin/user/user-list";
    }

    @GetMapping("/user/{id}/reset")
    public String resetPassword(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("id", id);
        return "admin/user/user-reset";
    }
}
