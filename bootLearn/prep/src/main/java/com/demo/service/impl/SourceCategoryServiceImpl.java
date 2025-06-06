package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.model.entity.SourceCategory;
import com.demo.service.ISourceCategoryService;
import com.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;

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
    @Value("${prep.dir}")
    private String prepDir;

    @Override
    public IPage<SourceCategory> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }

    @Override
    public Result saveCate(SourceCategory sourceCategory) {
        File sourceFolder = new File(prepDir + sourceCategory.getFolderName());
        if (sourceFolder.exists()) {
            return Result.error("对应文件夹已存在");
        } else {
            boolean mkdir = sourceFolder.mkdir();
            if (!mkdir) return Result.error("新建文件夹失败");
        }
        // 入库
        sourceCategory.setCreateTime(LocalDateTime.now());
        return Result.choice("新增", this.save(sourceCategory));
    }

    @Override
    public Result updateCate(SourceCategory sourceCategory) {
        String oldFolderName = baseMapper.queryFolderName(sourceCategory.getCategoryId());
        String folderName = sourceCategory.getFolderName();
        if (folderName == null || folderName.isBlank())
            return Result.error("文件夹名称不能为空");
        if (!oldFolderName.equals(folderName)) {
            File oldDir = new File(prepDir + oldFolderName);
            if (oldDir.exists()) {
                File newDir = new File(prepDir + folderName);
                boolean renameTo = oldDir.renameTo(newDir);
                if (!renameTo) return Result.error("文件夹更名失败");
            } else return Result.error("旧文件夹不存在，请确认");
        }
        return Result.choice("修改", this.updateById(sourceCategory));
    }

    @Override
    public Result removeCate(Integer id) {
        String oldFolderName = baseMapper.queryFolderName(id);
        File file = new File(prepDir + oldFolderName);
        if (file.exists()) {
            FileUtil.deleteFolderRecursively(file.toPath());
        }
        return Result.choice("删除", this.removeById(id));
    }
}
