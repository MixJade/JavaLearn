package com.forge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forge.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 员工表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Select("select employee_level from employee WHERE employee_username=#{username} and is_del=0")
    int selectLevel(String username);
    @Select("select employee_id,employee_username,employee_name,employee_tel,employee_photo " +
            "from employee WHERE employee_username=#{username} and is_del=0")
    Employee selectAName(String username);

    boolean deleteId(@Param("delDate") String delDate, @Param("employeeId") long employeeId);

    int selectEmployeeCount(@Param("employeeName") String employeeName);

    List<Employee> selectEmployeePage(@Param("employeeName") String employeeName, @Param("begin") int begin, @Param("pageItem") int pageItem);

}
