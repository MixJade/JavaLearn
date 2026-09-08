package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.SourceImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    List<SourceImage> getAll(Integer cateId);

    String getImgEnd(Integer id);

    void insertSourceImg(SourceImage sourceImage);
}
