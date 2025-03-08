package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.Dog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 狗 Mapper 接口
 *
 * @author MixJade
 * @since 2024-02-21
 */
@Mapper
public interface DogMapper extends BaseMapper<Dog> {
    // 查询所有的狗(带有特质)
    List<Dog> queryAllDog();
}
