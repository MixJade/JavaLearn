package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.PaperRecord;

/**
 * <p>
 * 试卷记录表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
public interface IPaperRecordService extends IService<PaperRecord> {
    IPage<PaperRecord> getByPage(int pageNum, int pageSize);
}
