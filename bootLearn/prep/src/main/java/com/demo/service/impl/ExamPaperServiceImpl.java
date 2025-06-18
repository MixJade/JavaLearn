package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.ExamPaperMapper;
import com.demo.model.dto.ExamPaperDto;
import com.demo.model.entity.ExamPaper;
import com.demo.service.IExamPaperService;
import com.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements IExamPaperService {
    @Value("${prep.dir}")
    private String prepDir;

    @Override
    public IPage<ExamPaper> getByPage(int pageNum, int pageSize, ExamPaperDto examPaperDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), examPaperDto);
    }

    @Override
    public Result addPaper(ExamPaper examPaper) {
        Result folderRes = FileUtil.creatFolder(prepDir, examPaper.getFolderName());
        if (folderRes.code() == 0) return folderRes;
        // 入库
        examPaper.setCreateDate(LocalDate.now());
        return Result.choice("新增", this.save(examPaper));
    }

    @Override
    public Result delPaper(Integer id) {
        // 查询下方文件数
        if (baseMapper.queryPaperNum(id) > 0) return Result.error("其下存在文件，不可删除");
        // 然后才是删除文件夹
        String oldFolderName = baseMapper.queryFolderName(id);
        FileUtil.deleteFolder(prepDir, oldFolderName);
        return Result.choice("删除", this.removeById(id));
    }

    @Override
    public Result updPaper(ExamPaper examPaper) {
        String oldFolderName = baseMapper.queryFolderName(examPaper.getPaperId());
        String folderName = examPaper.getFolderName();
        Result folderRes = FileUtil.updateFolderName(prepDir, oldFolderName, folderName);
        if (folderRes.code() == 0) return folderRes;
        return Result.choice("修改", this.updateById(examPaper));
    }
}
