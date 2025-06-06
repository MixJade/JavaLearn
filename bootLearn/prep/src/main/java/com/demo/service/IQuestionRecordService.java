package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.QuestionRecord;

/**
 * <p>
 * 题目记录表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
public interface IQuestionRecordService extends IService<QuestionRecord> {
    IPage<QuestionRecord> getByPage(int pageNum, int pageSize);
}
