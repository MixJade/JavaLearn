package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ExamPaperMapper;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;
import com.demo.service.IExamPaperService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 试卷表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements IExamPaperService {
    @Override
    public List<ExamPaperVo> getList() {
        return baseMapper.getList();
    }
}
