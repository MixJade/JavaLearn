package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.PaperRecord;
import com.demo.mapper.PaperRecordMapper;
import com.demo.service.IPaperRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 试卷记录表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Service
public class PaperRecordServiceImpl extends ServiceImpl<PaperRecordMapper, PaperRecord> implements IPaperRecordService {
    @Override
    public IPage<PaperRecord> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
