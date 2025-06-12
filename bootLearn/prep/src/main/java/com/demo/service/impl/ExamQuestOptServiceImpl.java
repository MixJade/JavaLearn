package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.ExamQuestOpt;
import com.demo.mapper.ExamQuestOptMapper;
import com.demo.service.IExamQuestOptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目选项表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class ExamQuestOptServiceImpl extends ServiceImpl<ExamQuestOptMapper, ExamQuestOpt> implements IExamQuestOptService {
    @Override
    public IPage<ExamQuestOpt> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
