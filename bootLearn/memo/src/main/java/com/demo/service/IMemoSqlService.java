package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.MemoPageDto;
import com.demo.model.entity.MemoSql;

/**
 * <p>
 * 备忘sql表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
public interface IMemoSqlService extends IService<MemoSql> {
    IPage<MemoSql> getByPage(int pageNum, int pageSize, MemoPageDto memoPageDto);
}
