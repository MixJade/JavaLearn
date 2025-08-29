package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
public interface IExamPaperService extends IService<ExamPaper> {
    IPage<ExamPaperVo> getByPage(int pageNum, int pageSize);
}
