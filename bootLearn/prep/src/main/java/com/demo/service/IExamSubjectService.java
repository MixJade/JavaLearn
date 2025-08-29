package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamSubject;

/**
 * <p>
 * 科目备忘表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
public interface IExamSubjectService extends IService<ExamSubject> {
    IPage<ExamSubject> getByPage(int pageNum, int pageSize);
}
