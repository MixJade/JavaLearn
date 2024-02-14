package com.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.domain.Students;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentsDao extends BaseMapper<Students> {
}
