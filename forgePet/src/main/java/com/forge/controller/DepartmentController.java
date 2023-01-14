package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.entity.Department;
import com.forge.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private IDepartmentService departService;

    @Autowired
    public void setDepartmentService(IDepartmentService departService) {
        this.departService = departService;
    }

    @GetMapping
    public List<NameDto> getAll() {
        return departService.selectName();
    }

    @GetMapping("/page")
    public Page<List<Department>> getPage(int numPage, int pageSize, String departmentName) {
        return departService.selectByPage(departmentName, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Department department) {
        return Result.choice("添加", departService.save(department));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", departService.deleteById(id));
    }

    @PutMapping
    public Result update(@RequestBody Department department) {
        return Result.choice("修改", departService.updateById(department));
    }

}
