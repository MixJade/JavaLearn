package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.ImageSourceMapper;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.model.entity.ImageSource;
import com.demo.service.IImageSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
    @Value("${prep.dir}")
    private String prepDir;
    private final SourceCategoryMapper sourceCategoryMapper;
    private static final Logger log = LoggerFactory.getLogger(ImageSourceServiceImpl.class);

    @Autowired
    public ImageSourceServiceImpl(SourceCategoryMapper sourceCategoryMapper) {
        this.sourceCategoryMapper = sourceCategoryMapper;
    }

    @Override
    public IPage<ImageSource> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
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
            if (dest.exists())
                return Result.error("该文件已存在");
            // 不存在就保存
            file.transferTo(dest);
            log.info("文件已转存:" + dest.getPath());
            return new Result(1, "保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件转存失败");
        }
    }
}
