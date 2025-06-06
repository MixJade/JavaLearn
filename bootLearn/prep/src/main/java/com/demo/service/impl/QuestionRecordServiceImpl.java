package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.QuestionRecord;
import com.demo.mapper.QuestionRecordMapper;
import com.demo.service.IQuestionRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目记录表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Service
public class QuestionRecordServiceImpl extends ServiceImpl<QuestionRecordMapper, QuestionRecord> implements IQuestionRecordService {
    @Override
    public IPage<QuestionRecord> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
