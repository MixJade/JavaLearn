package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.SourceImageMapper;
import com.demo.model.dto.SourceImgDto;
import com.demo.model.entity.SourceImage;
import com.demo.service.ISourceImageService;
import com.demo.service.OcrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 题源图片表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class SourceImageServiceImpl extends ServiceImpl<SourceImageMapper, SourceImage> implements ISourceImageService {
    private static final Logger log = LoggerFactory.getLogger(SourceImageServiceImpl.class);
    @Value("${prep.dir}")
    private String prepDir;
    private final OcrService ocrService;

    @Autowired
    public SourceImageServiceImpl(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @Override
    public IPage<SourceImage> getByPage(int pageNum, int pageSize, SourceImgDto sourceImgDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), sourceImgDto);
    }

    @Override
    public Result saveImg(MultipartFile file, SourceImage sourceImage) {
        if (file.isEmpty() || file.getOriginalFilename() == null)
            return Result.error("请上传图片");

        try {
            // 打印上传文件的名称
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            // 保存图片，并获得其主键
            sourceImage.setFileEnd(suffix);
            baseMapper.insertSourceImg(sourceImage);
            // 得到文件名
            String imgFileName = "sou" + sourceImage.getImageId() + suffix;
            // 转存文件到指定目录
            File dest = new File(prepDir + imgFileName);
            // 不存在就保存
            // 文件如果存在，可能是之前的数据被删了但图片没删，就不做强校验
            if (!dest.exists()) {
                file.transferTo(dest);
                log.info("文件已转存:" + dest.getPath());
            }
            // 返回文件名
            return Result.suc(imgFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件转存失败");
        }
    }

    @Override
    public Result ocr(Integer id) {
        String ocrRes = ocrService.normalOCR(getImgPath(id));
        lambdaUpdate().set(SourceImage::getOcrResult, ocrRes)
                .set(SourceImage::getOcrTime, LocalDateTime.now())
                .eq(SourceImage::getImageId, id)
                .update();
        return Result.suc(ocrRes);
    }

    @Override
    public String getImgPath(Integer id) {
        return prepDir + "sou" + id + baseMapper.getImgEnd(id);
    }
}
