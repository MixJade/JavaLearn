package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.entity.RecordPaper;
import com.demo.mapper.RecordPaperMapper;
import com.demo.service.IRecordPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 试卷记录表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class RecordPaperServiceImpl extends ServiceImpl<RecordPaperMapper, RecordPaper> implements IRecordPaperService {
    @Override
    public IPage<RecordPaper> getByPage(int pageNum, int pageSize) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize));
    }
}
