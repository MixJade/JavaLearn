package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.ImgSourceDto;
import com.demo.model.entity.ImageSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    IPage<ImageSource> getByPage(IPage<ImageSource> page, @Param("dto") ImgSourceDto imgSourceDto);
}
