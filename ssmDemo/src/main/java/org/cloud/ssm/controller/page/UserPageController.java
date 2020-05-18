package org.cloud.ssm.controller.page;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.cloud.ssm.common.utils.ShiroUtil;
import org.cloud.ssm.entity.User;
import org.cloud.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UserPageController {

    @Autowired
    private IUserService userService;

    /**
     * 跳转到个人信息页面
     */
    @GetMapping("/userInfo")
    @RequiresPermissions("index")
    public String toUserInfo(Model model) {
        User user = ShiroUtil.getSubject();
        model.addAttribute("user", userService.findUserInfoByUsername(user.getUsername()));
        return "admin/user/user-info";
    }

    @GetMapping("/editpass")
    @RequiresPermissions("index")
    public String editPassword(ModelMap model) {
        User user = ShiroUtil.getSubject();
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
