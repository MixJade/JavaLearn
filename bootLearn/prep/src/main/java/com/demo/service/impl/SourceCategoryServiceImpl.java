package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.model.entity.SourceCategory;
import com.demo.model.vo.CateLabelVo;
import com.demo.model.vo.SourceCateVo;
import com.demo.service.ISourceCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题源分类表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
@Service
public class SourceCategoryServiceImpl extends ServiceImpl<SourceCategoryMapper, SourceCategory> implements ISourceCategoryService {

    @Override
    public List<SourceCateVo> getList() {
        return baseMapper.getList();
    }

    @Override
    public List<CateLabelVo> getCateLabel() {
        return baseMapper.getCateLabel();
    }
}
