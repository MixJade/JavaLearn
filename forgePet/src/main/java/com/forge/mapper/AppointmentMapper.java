package com.forge.mapper;

import com.forge.dto.AppointmentDto;
import com.forge.dto.AppointmentDto;
import com.forge.entity.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 挂号单表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2022-12-22
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
    boolean deleteId(@Param("delDate") String delDate, @Param("appointmentId") long appointmentId);

    boolean deleteIdGroup(@Param("delDate") String delDate, @Param("idGroup") long[] idGroup);

    int selectAppointmentCount(@Param("seaName") String seaName,@Param("seaType") int seaType);

    List<AppointmentDto> selectAppointmentPage(@Param("seaName") String seaName, @Param("seaType") int seaType, @Param("begin") int begin, @Param("pageItem") int pageItem);

}
