package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.AppointmentDto;
import com.forge.dto.Page;
import com.forge.entity.Appointment;
import com.forge.mapper.AppointmentMapper;
import com.forge.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 挂号单表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {


    private AppointmentMapper appointmentMapper;

    @Autowired
    public void setAppointmentMapper(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public boolean deleteById(long appointmentId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return appointmentMapper.deleteId(delDate, appointmentId);
    }

    @Override
    public boolean deleteByIds(long[] idGroup) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return appointmentMapper.deleteIdGroup(delDate, idGroup);
    }

    @Override
    public Page<List<AppointmentDto>> selectByPage(String seaName, int seaType, int numPage, int pageSize) {
        if (seaName != null && !seaName.equals("")) {
            seaName = "%" + seaName + "%";
        }
        int maxCount = appointmentMapper.selectAppointmentCount(seaName, seaType);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<AppointmentDto> appointmentList = appointmentMapper.selectAppointmentPage(seaName, seaType, needBegin, pageSize);
        return Page.page(appointmentList, maxCount);
    }
}
