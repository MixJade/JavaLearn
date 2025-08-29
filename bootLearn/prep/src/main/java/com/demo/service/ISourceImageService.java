package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.common.Result;
import com.demo.model.dto.SourceImgDto;
import com.demo.model.entity.SourceImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 题源图片表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
public interface ISourceImageService extends IService<SourceImage> {
    IPage<SourceImage> getByPage(int pageNum, int pageSize, SourceImgDto sourceImgDto);

    /**
     * 保存图片
     *
     * @param file   图片数据
     * @param sourceImage 图片备注+分类
     */
    Result saveImg(MultipartFile file, SourceImage sourceImage);

    /**
     * 识别图片文字
     *
     * @param id 图片主键
     */
    Result ocr(Integer id);

    /**
     * 获取图片路径
     *
     * @param id 图片主键
     */
    String getImgPath(Integer id);
}
