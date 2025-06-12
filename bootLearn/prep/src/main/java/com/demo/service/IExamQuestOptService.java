package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamQuestOpt;

/**
 * <p>
 * 题目选项表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
public interface IExamQuestOptService extends IService<ExamQuestOpt> {
    IPage<ExamQuestOpt> getByPage(int pageNum, int pageSize);
}
