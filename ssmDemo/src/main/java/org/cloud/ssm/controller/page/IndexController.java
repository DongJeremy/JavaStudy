package org.cloud.ssm.controller.page;

import javax.annotation.Resource;

import org.cloud.ssm.service.IMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
public class IndexController {

    @Resource
    private IMenuService service;

    @GetMapping(value = { "/admin", "/admin/main" })
    public String index(ModelMap model) {
        model.addAttribute("menus", service.getTreeData(5));
        return "admin/index";
    }

    @Resource
    private WebApplicationContext applicationContext;

    @GetMapping("/403")
    public String forbidden() {
        return "error/403";
    }

    @GetMapping("/404")
    public String unauthorizedPage() {
        return "error/404";
    }

    @GetMapping("/500")
    public String error() {
        return "error/500";
    }

}
