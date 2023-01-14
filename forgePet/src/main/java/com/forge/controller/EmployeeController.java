package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.Page;
import com.forge.entity.Employee;
import com.forge.service.IEmployeeService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private IEmployeeService employeeService;

    @Autowired
    public void setEmployeeService(IEmployeeService departService) {
        this.employeeService = departService;
    }

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.list();
    }

    @GetMapping("/page")
    public Page<List<Employee>> getPage(int numPage, int pageSize, String employeeName) {
        return employeeService.selectByPage(employeeName, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Employee employee) {
        if (employee.getEmployeePassword() == null || employee.getEmployeePassword().equals("")) {
            employee.setEmployeePassword("6b6864bf70c40ccbc2752cd9ef11e77b");
        }
        return Result.choice("添加", employeeService.save(employee));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", employeeService.deleteById(id));
    }

    @PutMapping
    public Result update(@RequestBody Employee employee) {
        return Result.choice("修改", employeeService.updateById(employee));
    }
}
