package org.cloud.ssm.controller.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.cloud.ssm.sys.info.Server;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequiresPermissions("index")
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
    @RequiresPermissions("index")
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

}
