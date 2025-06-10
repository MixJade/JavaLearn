package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.common.Result;
import com.demo.model.entity.ImageSource;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 保存图片
     *
     * @param file   图片数据
     * @param cateId 文件夹主键
     */
    Result saveImg(MultipartFile file, int cateId);
}
