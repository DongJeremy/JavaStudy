package org.cloud.ssm.controller;

import java.util.HashMap;
import java.util.Map;

import org.cloud.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> login(@RequestParam(required = true) String username,
            @RequestParam(required = true) String password) {
        Map<String, Object> user = userService.findUserByUserName(username, password);
        System.out.println(user);
        return user;
    }

    @PostMapping("/member_role")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public Map<String, Object> memberRole() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "请求成功");
        return map;
    }

    @PostMapping("/stock_role")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_STOCK')")
    public Map<String, Object> stockRole() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "请求成功");
        return map;
    }

    @GetMapping("/test")
    @ResponseBody
    public Map<String, Object>  test() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("message", "请求成功");
        return map;
    }
}
