package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.Students;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentsDao extends BaseMapper<Students> {
}
