package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.RecordQuest;
import com.demo.mapper.RecordQuestMapper;
import com.demo.service.IRecordQuestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目记录表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class RecordQuestServiceImpl extends ServiceImpl<RecordQuestMapper, RecordQuest> implements IRecordQuestService {
    @Override
    public IPage<RecordQuest> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
