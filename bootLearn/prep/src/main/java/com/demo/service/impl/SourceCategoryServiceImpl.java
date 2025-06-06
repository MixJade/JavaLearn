package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.SourceCategory;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.service.ISourceCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题源分类表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Service
public class SourceCategoryServiceImpl extends ServiceImpl<SourceCategoryMapper, SourceCategory> implements ISourceCategoryService {
    @Override
    public IPage<SourceCategory> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
