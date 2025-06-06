package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ImageSource;

/**
 * <p>
 * 题源图片表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
public interface IImageSourceService extends IService<ImageSource> {
    IPage<ImageSource> getByPage(int pageNum, int pageSize);
}
