package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.common.Result;
import com.demo.model.entity.SourceCategory;
import com.demo.model.vo.CateLabelVo;
import com.demo.model.vo.SourceCateVo;

import java.util.List;

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

    /**
     * 查询题源分类的下拉框
     * @return 题源分类主键+分类名称
     */
    List<CateLabelVo> getCateLabel();
}
