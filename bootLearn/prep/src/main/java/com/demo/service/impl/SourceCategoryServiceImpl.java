package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.SourceCategoryMapper;
import com.demo.model.entity.SourceCategory;
import com.demo.model.vo.CateLabelVo;
import com.demo.model.vo.SourceCateVo;
import com.demo.service.ISourceCategoryService;
import com.demo.utils.MyFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    private final MyFileUtil fileUtil;

    @Autowired
    public SourceCategoryServiceImpl(MyFileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public IPage<SourceCateVo> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }

    @Override
    public Result saveCate(SourceCategory sourceCategory) {
        Result folderRes = fileUtil.creatFolder(sourceCategory.getFolderName());
        if (folderRes.code() == 0) return folderRes;
        // 入库
        sourceCategory.setCreateDate(LocalDate.now());
        return Result.choice("新增", this.save(sourceCategory));
    }

    @Override
    public Result updateCate(SourceCategory sourceCategory) {
        String oldFolderName = baseMapper.queryFolderName(sourceCategory.getCategoryId());
        String folderName = sourceCategory.getFolderName();
        Result folderRes = fileUtil.updateFolderName(oldFolderName, folderName);
        if (folderRes.code() == 0) return folderRes;
        return Result.choice("修改", this.updateById(sourceCategory));
    }

    @Override
    public Result removeCate(Integer id) {
        // 查询下方文件数
        int imgNUm = baseMapper.queryImgNum(id);
        if (imgNUm > 0) return Result.error("其下存在文件，不可删除");
        // 然后才是删除文件夹
        String oldFolderName = baseMapper.queryFolderName(id);
        fileUtil.deleteFolder(oldFolderName);
        return Result.choice("删除", this.removeById(id));
    }

    @Override
    public List<CateLabelVo> getCateLabel() {
        return baseMapper.getCateLabel();
    }
}
