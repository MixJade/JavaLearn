package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.RecordPaper;

/**
 * <p>
 * 试卷记录表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
public interface IRecordPaperService extends IService<RecordPaper> {
    IPage<RecordPaper> getByPage(int pageNum, int pageSize);
}
