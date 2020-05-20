package org.cloud.ssm.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.cloud.ssm.common.util.PageResultBean;
import org.cloud.ssm.common.util.ResultBean;
import org.cloud.ssm.system.model.Dept;
import org.cloud.ssm.system.service.IDeptService;
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
@RequestMapping("/api/dept")
public class DeptController {

    @Resource
    private IDeptService service;

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Dept> listDept(@RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Dept> depts = service.getAll(page, limit);
        long count = service.getCount(new Dept());
        return new PageResultBean<Dept>(count, depts);
    }

    @PostMapping
    @ResponseBody
    public ResultBean addDept(@RequestBody Dept dept) {
        return ResultBean.success(service.save(dept));
    }

    @PutMapping
    @ResponseBody
    public ResultBean updateDept(@RequestBody Dept dept) {
        service.save(dept);
        return ResultBean.success();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultBean deleteDept(@PathVariable Long id) {
        service.deleteById(id);
        return ResultBean.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultBean deleteBatchDept(@RequestBody List<String> ids) {
        int sum = 0;
        for (String id : ids) {
            Dept dept = service.getById(Long.parseLong(id)).orElse(new Dept());
            sum += service.deleteById(dept.getId());
        }
        return ResultBean.successData(sum);
    }

}
