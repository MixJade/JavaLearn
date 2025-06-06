package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.Paper;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
public interface IPaperService extends IService<Paper> {
    IPage<Paper> getByPage(int pageNum, int pageSize);
}
