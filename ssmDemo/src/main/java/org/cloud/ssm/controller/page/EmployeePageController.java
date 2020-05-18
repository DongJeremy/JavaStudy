package org.cloud.ssm.controller.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloud.ssm.common.annotation.OperationLog;
import org.cloud.ssm.common.bean.ResultBean;
import org.cloud.ssm.common.utils.ExcelUtils;
import org.cloud.ssm.common.utils.MapperUtils;
import org.cloud.ssm.entity.Employee;
import org.cloud.ssm.entity.vo.EmployeeVO;
import org.cloud.ssm.service.IDepartmentService;
import org.cloud.ssm.service.IEmployeeService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/admin")
public class EmployeePageController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Mapper mapper ;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @OperationLog("获取用户列表")
    @GetMapping("/empView")
    public String empListView(ModelMap model) throws Exception {
        model.addAttribute("departmentList", departmentService.getAll());
        return "admin/emp/emp-list";
    }

    @GetMapping("/empChangeView/{id}")
    public String empUpdatePage(ModelMap model, @PathVariable("id") Long id) throws Exception {
        model.addAttribute("employee", employeeService.getById(id).orElse(new Employee()));
        model.addAttribute("departmentList", departmentService.getAll());
        return "admin/emp/emp-add";
    }

    @GetMapping("/empChangeView")
    public String empAddPage(ModelMap model) throws Exception {
        model.addAttribute("departmentList", departmentService.getAll());
        return "admin/emp/emp-add";
    }

    @GetMapping("/empDetailsView/{id}")
    public String empDetailsView(ModelMap model, @PathVariable("id") Long id) throws Exception {
        model.addAttribute("employee", employeeService.getById(id).orElse(new Employee()));
        return "admin/emp/emp-details";
    }

    @GetMapping("/empView/excel/download")
    public void empViewDownload(HttpServletResponse response) throws IOException {
        String excelFileName = "employee";
        final List<Employee> list = employeeService.getAll();
        List<EmployeeVO> voList = MapperUtils.map(mapper, list, EmployeeVO.class);
        logger.info("download excel file to local.");
        ExcelUtils.exportToFile(voList, excelFileName, response);
    }

    @PostMapping("/empImport")
    public @ResponseBody ResultBean importEmployee(HttpServletRequest request) throws IOException, Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        final List<EmployeeVO> listObjects = ExcelUtils.importFromFile(file, EmployeeVO.class);
        List<Employee> employeeList = MapperUtils.map(mapper, listObjects, Employee.class);
        long ret = employeeService.batchSaveEmployee(employeeList);
        if (ret == 1L) {
            logger.info("upload excel file successful.");
            return ResultBean.success();
        }
        logger.error("upload excel file fail.");
        return ResultBean.error("import error");
    }
}
