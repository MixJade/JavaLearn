package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.MemoSqlMapper;
import com.demo.model.dto.MemoPageDto;
import com.demo.model.entity.MemoSql;
import com.demo.service.IMemoSqlService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 备忘sql表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@Service
public class MemoSqlServiceImpl extends ServiceImpl<MemoSqlMapper, MemoSql> implements IMemoSqlService {
    @Override
    public IPage<MemoSql> getByPage(int pageNum, int pageSize, MemoPageDto memoPageDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), memoPageDto);
    }
}
