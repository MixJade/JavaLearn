package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.ImageSourceMapper;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.model.dto.ImgSourceDto;
import com.demo.model.entity.ImageSource;
import com.demo.model.vo.ImgSourceVo;
import com.demo.service.IImageSourceService;
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
 * @since 2025-06-06
 */
@Service
public class ImageSourceServiceImpl extends ServiceImpl<ImageSourceMapper, ImageSource> implements IImageSourceService {
    private static final Logger log = LoggerFactory.getLogger(ImageSourceServiceImpl.class);
    @Value("${prep.dir}")
    private String prepDir;
    private final SourceCategoryMapper sourceCategoryMapper;
    private final OcrService ocrService;

    @Autowired
    public ImageSourceServiceImpl(SourceCategoryMapper sourceCategoryMapper, OcrService ocrService) {
        this.sourceCategoryMapper = sourceCategoryMapper;
        this.ocrService = ocrService;
    }

    @Override
    public IPage<ImgSourceVo> getByPage(int pageNum, int pageSize, ImgSourceDto imgSourceDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), imgSourceDto);
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
        ImageSource source = lambdaQuery().select(ImageSource::getCategoryId, ImageSource::getFileName)
                .eq(ImageSource::getSourceId, id)
                .one();
        String folderName = sourceCategoryMapper.queryFolderName(source.getCategoryId());
        String ocrRes = ocrService.normalOCR(prepDir + folderName + "\\" + source.getFileName());
        lambdaUpdate().set(ImageSource::getOcrResult, ocrRes)
                .set(ImageSource::getOcrTime, LocalDateTime.now())
                .eq(ImageSource::getSourceId, id)
                .update();
        return new Result(1, ocrRes);
    }
}
