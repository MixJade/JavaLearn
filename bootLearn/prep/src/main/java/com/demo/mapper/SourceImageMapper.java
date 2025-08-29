package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.SourceImgDto;
import com.demo.model.entity.SourceImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 题源图片表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Mapper
public interface SourceImageMapper extends BaseMapper<SourceImage> {
    IPage<SourceImage> getByPage(IPage<SourceImage> page, @Param("dto") SourceImgDto sourceImgDto);

    String getImgName(Integer id);

    int insertSourceImg(SourceImage sourceImage);
}
