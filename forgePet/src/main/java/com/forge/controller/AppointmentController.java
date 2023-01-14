package com.forge.controller;

import com.forge.common.Result;
import com.forge.dto.AppointmentDto;
import com.forge.dto.Page;
import com.forge.entity.Appointment;
import com.forge.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 挂号单表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private IAppointmentService appointmentService;

    @Autowired
    public void setAppointmentService(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.list();
    }

    @GetMapping("/page")
    public Page<List<AppointmentDto>> getPage(int numPage, int pageSize, String seaName, int seaType) {
        return appointmentService.selectByPage(seaName, seaType, numPage, pageSize);
    }

    @PostMapping
    public Result save(@RequestBody Appointment appointment) {
        return Result.choice("添加", appointmentService.save(appointment));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return Result.choice("删除单个", appointmentService.deleteById(id));
    }

    @DeleteMapping("/batch/{ids}")
    public Result deleteGroup(@PathVariable long[] ids) {
        return Result.choice("删除多个", appointmentService.deleteByIds(ids));
    }

    @PutMapping
    public Result update(@RequestBody Appointment appointment) {
        return Result.choice("修改", appointmentService.updateById(appointment));
    }

}
