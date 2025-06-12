package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamQuest;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
public interface IExamQuestService extends IService<ExamQuest> {
    IPage<ExamQuest> getByPage(int pageNum, int pageSize);
}
