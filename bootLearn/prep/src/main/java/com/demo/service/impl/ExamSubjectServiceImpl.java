package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ExamSubjectMapper;
import com.demo.model.entity.ExamSubject;
import com.demo.service.IExamSubjectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 科目表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-25
 */
@Service
public class ExamSubjectServiceImpl extends ServiceImpl<ExamSubjectMapper, ExamSubject> implements IExamSubjectService {
    @Override
    public IPage<ExamSubject> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
