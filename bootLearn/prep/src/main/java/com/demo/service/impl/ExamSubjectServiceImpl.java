package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.Result;
import com.demo.mapper.ExamSubjectMapper;
import com.demo.model.entity.ExamSubject;
import com.demo.service.IExamSubjectService;
import com.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 * 科目表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class ExamSubjectServiceImpl extends ServiceImpl<ExamSubjectMapper, ExamSubject> implements IExamSubjectService {
    @Value("${prep.dir}")
    private String prepDir;

    @Override
    public IPage<ExamSubject> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }

    @Override
    public Result addSubject(ExamSubject examSubject) {
        Result folderRes = FileUtil.creatFolder(prepDir, examSubject.getFolderName());
        if (folderRes.code() == 0) return folderRes;
        // 入库
        examSubject.setCreateDate(LocalDate.now());
        return Result.choice("新增", this.save(examSubject));
    }

    @Override
    public Result delSubject(Integer id) {
        // 查询下方文件数
        if (baseMapper.queryPaperNum(id) > 0) return Result.error("其下存在文件，不可删除");
        // 然后才是删除文件夹
        String oldFolderName = baseMapper.queryFolderName(id);
        FileUtil.deleteFolder(prepDir, oldFolderName);
        return Result.choice("删除", this.removeById(id));
    }

    @Override
    public Result updSubject(ExamSubject examSubject) {
        String oldFolderName = baseMapper.queryFolderName(examSubject.getSubjectId());
        String folderName = examSubject.getFolderName();
        Result folderRes = FileUtil.updateFolderName(prepDir, oldFolderName, folderName);
        if (folderRes.code() == 0) return folderRes;
        return Result.choice("修改", this.updateById(examSubject));
    }
}
