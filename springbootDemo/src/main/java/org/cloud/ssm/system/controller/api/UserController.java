package org.cloud.ssm.system.controller.api;

import java.util.List;

import javax.annotation.Resource;

import org.cloud.ssm.common.annotation.OperationLog;
import org.cloud.ssm.common.domain.SysUser;
import org.cloud.ssm.common.service.UserService;
import org.cloud.ssm.common.util.PageResultBean;
import org.cloud.ssm.common.util.ResultBean;
import org.cloud.ssm.system.model.vo.UserOnline;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @OperationLog("获取在线用户列表")
    @GetMapping("/onlinelist")
    @ResponseBody
    public PageResultBean<UserOnline> listUsers(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<UserOnline> userOnlines = userService.getOnlineUsers(page, limit);
        long count = userService.getOnlineUserCount();
        return new PageResultBean<UserOnline>(count, userOnlines);
    }

    @PostMapping("/kickout")
    @ResponseBody
    public ResultBean forceLogout(String sessionId) {
        userService.forceLogout(sessionId);
        return ResultBean.success();
    }

    @OperationLog("获取用户列表")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<SysUser> listUser(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<SysUser> emps = userService.getAll(page, limit);
        long count = userService.getCount(new SysUser());
        return new PageResultBean<SysUser>(count, emps);
    }

    @OperationLog("添加用户")
    @PostMapping
    @ResponseBody
    public ResultBean addUser(@RequestBody SysUser user) {
        return ResultBean.success(userService.save(user));
    }

    @OperationLog("编辑用户")
    @PutMapping
    @ResponseBody
    public ResultBean updateUser(@RequestBody SysUser user) {
        userService.save(user);
        return ResultBean.success();
    }

    @PostMapping("/userInfo")
    @ResponseBody
    public ResultBean userInfo(@RequestBody SysUser user){
        // 保存数据
        userService.updateUserInfoByPrimaryKey(user);
        return ResultBean.success();
    }

    @OperationLog("刪除用户")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultBean deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResultBean.success();
    }

    @OperationLog("删除账号")
    @PostMapping("/{id}/disable")
    @ResponseBody
    public ResultBean disable(@PathVariable("id") Long id) {
        return ResultBean.success(userService.disableUserByID(id));
    }

    @OperationLog("激活账号")
    @PostMapping("/{id}/enable")
    @ResponseBody
    public ResultBean enable(@PathVariable("id") Long id) {
        return ResultBean.success(userService.enableUserByID(id));
    }

    @OperationLog("重置密码")
    @PostMapping("/{id}/reset")
    @ResponseBody
    public ResultBean resetPassword(@PathVariable("id") Long id, String original, String password) {
        boolean resetPass = userService.updatePasswordByUserId(id, original, password);
        if (!resetPass) {
            return ResultBean.error("原密码错误，重置密码失败");
        }
        return ResultBean.success();
    }
}
