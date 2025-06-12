package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.mapper.SourceImageMapper;
import com.demo.model.dto.SourceImgDto;
import com.demo.model.entity.SourceImage;
import com.demo.model.vo.SourceImgVo;
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
    private final SourceCategoryMapper sourceCategoryMapper;
    private final OcrService ocrService;

    @Autowired
    public SourceImageServiceImpl(SourceCategoryMapper sourceCategoryMapper, OcrService ocrService) {
        this.sourceCategoryMapper = sourceCategoryMapper;
        this.ocrService = ocrService;
    }

    @Override
    public IPage<SourceImgVo> getByPage(int pageNum, int pageSize, SourceImgDto sourceImgDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), sourceImgDto);
    }

    @Override
    public Result saveImg(MultipartFile file, int cateId) {
        if (file.isEmpty())
            return Result.error("请上传图片");

        try {
            String folderName = sourceCategoryMapper.queryFolderName(cateId);
            // 打印上传文件的名称
            String fileName = file.getOriginalFilename();
            log.info("上传文件:{}，文件夹id:{}", fileName, cateId);
            // 转存文件到指定目录
            String uploadDir = prepDir + folderName; // 需替换为实际的目录路径
            File dest = new File(uploadDir + "\\" + fileName);
            // 不存在就保存
            // 文件如果存在，可能是之前的数据被删了但图片没删，就不做强校验
            if (!dest.exists()) {
                file.transferTo(dest);
                log.info("文件已转存:" + dest.getPath());
            }
            // 返回文件名
            return new Result(1, fileName);
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
        return new Result(1, ocrRes);
    }

    @Override
    public String getImgPath(Integer id) {
        SourceImage source = lambdaQuery().select(SourceImage::getCategoryId, SourceImage::getFileName)
                .eq(SourceImage::getImageId, id)
                .one();
        String folderName = sourceCategoryMapper.queryFolderName(source.getCategoryId());
        return prepDir + folderName + "\\" + source.getFileName();
    }
}
