package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.DoctorDto;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.entity.Doctor;
import com.forge.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 医生表，外键部门 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private IDoctorService doctorService;

    @Autowired
    public void setDoctorService(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<NameDto> getAll() {
        return doctorService.selectName();
    }
    @GetMapping("/department")
    public List<NameDto> getByDepartment(long departmentId) {
        return doctorService.selectByDepartment(departmentId);
    }

    @GetMapping("/page")
    public Page<List<DoctorDto>> getPage(int numPage, int pageSize, String doctorName, String departmentName) {
        return doctorService.selectByPage(doctorName, departmentName, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Doctor doctor) {
        return Result.choice("添加", doctorService.save(doctor));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", doctorService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", doctorService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody Doctor doctor) {
        return Result.choice("修改", doctorService.updateById(doctor));
    }
}
