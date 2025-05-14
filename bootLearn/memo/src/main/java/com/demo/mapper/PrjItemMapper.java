package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.PrjPageDto;
import com.demo.model.entity.PrjItem;
import com.demo.model.vo.PrjItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 项目分类表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@Mapper
public interface PrjItemMapper extends BaseMapper<PrjItem> {
    IPage<PrjItemVo> getByPage(IPage<PrjItem> objectPage, @Param("dto") PrjPageDto prjPageDto);
}
