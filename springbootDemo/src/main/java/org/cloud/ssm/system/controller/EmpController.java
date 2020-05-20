package org.cloud.ssm.system.controller;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.cloud.ssm.common.annotation.OperationLog;
import org.cloud.ssm.common.util.PageResultBean;
import org.cloud.ssm.common.util.ResultBean;
import org.cloud.ssm.system.model.Emp;
import org.cloud.ssm.system.service.IDeptService;
import org.cloud.ssm.system.service.IEmpService;
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
@RequestMapping("/api/emp")
public class EmpController {

    @Resource
    private IEmpService service;

    @Resource
    private IDeptService deptService;

    @OperationLog("获取雇员列表")
    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Emp> listEmp(@RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "deptname", required = false) String deptName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<Emp> emps = null;
        long count = 0;
        if ((deptName == null) && (username == null)) {
            emps = service.getAll(page, limit);
            count = service.getCount(new Emp());
        } else {
            emps = service.getAllByCondition(username, deptName, page, limit);
            count = service.getCountByCondition(username, deptName);
        }
        return new PageResultBean<Emp>(count, emps);
    }

    @OperationLog("添加雇员")
    @PostMapping
    @ResponseBody
    public ResultBean addEmp(@RequestBody Emp emp) {
        emp.setUserId(UUID.randomUUID().toString());
        return ResultBean.success(service.save(emp));
    }

    @OperationLog("编辑雇员")
    @PutMapping
    @ResponseBody
    public ResultBean updateEmp(@RequestBody Emp emp) {
        service.save(emp);
        return ResultBean.success();
    }

    @OperationLog("刪除雇员")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultBean deleteEmp(@PathVariable Long id) {
        service.deleteById(id);
        return ResultBean.success();
    }

    /**
     * 批量删除
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultBean removeEmp(@RequestBody List<String> ids) {
        int sum = 0;
        for (String id : ids) {
            Emp emp = service.getById(Long.parseLong(id)).orElse(new Emp());
            sum += service.deleteById(emp.getId());
        }
        return ResultBean.successData(sum);
    }

    @OperationLog("禁用雇员")
    @PostMapping("/{id}/disable")
    @ResponseBody
    public ResultBean disable(@PathVariable("id") Long id) {
        return ResultBean.success(service.disableUserByID(id));
    }

    @OperationLog("激活雇员")
    @PostMapping("/{id}/enable")
    @ResponseBody
    public ResultBean enable(@PathVariable("id") Long id) {
        return ResultBean.success(service.enableUserByID(id));
    }

}
