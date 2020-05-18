package org.cloud.ssm.controller.page;

import org.cloud.ssm.entity.Department;
import org.cloud.ssm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DepartmentPageController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping("/deptView")
    public String deptListView(ModelMap model) throws Exception {
        return "admin/dept/dept-list";
    }

    @GetMapping("/deptChangeView/{id}")
    public String deptUpdatePage(ModelMap model, @PathVariable("id") Long id) throws Exception {
        model.addAttribute("department", departmentService.getById(id).orElse(new Department()));
        return "admin/dept/dept-add";
    }

    @GetMapping("/deptChangeView")
    public String deptAddPage(ModelMap model) throws Exception {
        return "admin/dept/dept-add";
    }
}
