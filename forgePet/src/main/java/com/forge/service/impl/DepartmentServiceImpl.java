package com.forge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forge.dto.NameDto;
import com.forge.dto.Page;
import com.forge.entity.Department;
import com.forge.mapper.DepartmentMapper;
import com.forge.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    private DepartmentMapper departmentMapper;

    @Autowired
    public void setDepartmentMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public boolean deleteById(long departmentId) {
        String delDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return departmentMapper.deleteId(delDate, departmentId);
    }

    @Override
    public Page<List<Department>> selectByPage(String departmentName, int numPage, int pageSize) {
        int maxCount = departmentMapper.selectDepartmentCount(departmentName);
        int needBegin = (numPage - 1) * pageSize;
        if (needBegin > maxCount) {
            needBegin = (maxCount / pageSize) * pageSize;
        }
        List<Department> departmentList = departmentMapper.selectDepartmentPage(departmentName, needBegin, pageSize);
        return Page.page(departmentList, maxCount);
    }

    @Override
    public List<NameDto> selectName() {
        return departmentMapper.selectName();
    }
}
