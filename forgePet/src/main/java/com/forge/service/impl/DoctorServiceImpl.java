package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.DoctorDto;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.entity.Doctor;
import com.forge.mapper.DoctorMapper;
import com.forge.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 医生表，外键部门 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2023-01-02
 */
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {
    private DoctorMapper doctorMapper;

    @Autowired
    public void setDoctorMapper(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public boolean deleteById(long doctorId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return doctorMapper.deleteId(delDate, doctorId);
    }

    @Override
    public boolean deleteByIds(long[] idGroup) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return doctorMapper.deleteIdGroup(delDate, idGroup);
    }

    @Override
    public Page<List<DoctorDto>> selectByPage(String doctorName, String departmentName, int numPage, int pageSize) {
        int maxCount = doctorMapper.selectDoctorCount(doctorName,departmentName);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<DoctorDto> doctorList = doctorMapper.selectDoctorPage(doctorName, departmentName, needBegin, pageSize);
        return Page.page(doctorList, maxCount);
    }

    @Override
    public List<NameDto> selectName() {
        return doctorMapper.selectName();
    }

    @Override
    public List<NameDto> selectByDepartment(long departmentId) {
        return doctorMapper.selectByDepartment(departmentId);
    }
}
