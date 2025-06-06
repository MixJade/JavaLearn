package com.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.entity.ImageSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题源图片表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Mapper
public interface ImageSourceMapper extends BaseMapper<ImageSource> {
    IPage<ImageSource> getByPage(IPage<ImageSource> page);
}
