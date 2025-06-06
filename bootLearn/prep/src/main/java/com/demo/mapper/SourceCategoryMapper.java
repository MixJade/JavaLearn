package com.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.entity.SourceCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题源分类表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Mapper
public interface SourceCategoryMapper extends BaseMapper<SourceCategory> {
    IPage<SourceCategory> getByPage(IPage<SourceCategory> page);

    // 查询旧的文件夹名称
    String queryFolderName(Integer categoryId);
}
