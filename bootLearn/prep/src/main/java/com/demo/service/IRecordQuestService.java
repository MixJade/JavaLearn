package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.RecordQuest;

/**
 * <p>
 * 题目记录表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
public interface IRecordQuestService extends IService<RecordQuest> {
    IPage<RecordQuest> getByPage(int pageNum, int pageSize);
}
