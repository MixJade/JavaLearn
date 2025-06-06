package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.ImageSource;
import com.demo.mapper.ImageSourceMapper;
import com.demo.service.IImageSourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题源图片表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Service
public class ImageSourceServiceImpl extends ServiceImpl<ImageSourceMapper, ImageSource> implements IImageSourceService {
    @Override
    public IPage<ImageSource> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
