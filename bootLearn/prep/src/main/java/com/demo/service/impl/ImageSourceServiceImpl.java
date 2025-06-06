package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ImageSourceMapper;
import com.demo.model.entity.ImageSource;
import com.demo.service.IImageSourceService;
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

}
