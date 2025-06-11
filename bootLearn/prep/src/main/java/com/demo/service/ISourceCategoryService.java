package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.common.Result;
import com.demo.model.entity.SourceCategory;
import com.demo.model.vo.SourceCateVo;

/**
 * <p>
 * 题源分类表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
public interface ISourceCategoryService extends IService<SourceCategory> {
    IPage<SourceCateVo> getByPage(int pageNum, int pageSize);

    Result saveCate(SourceCategory sourceCategory);

    Result updateCate(SourceCategory sourceCategory);

    Result removeCate(Integer id);
}
