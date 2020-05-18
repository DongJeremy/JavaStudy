package org.cloud.ssm.controller;

import org.cloud.ssm.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/")
    public ModelAndView restEmployee() {
        return new ModelAndView("/employee", "command", new Employee());
    }
}
