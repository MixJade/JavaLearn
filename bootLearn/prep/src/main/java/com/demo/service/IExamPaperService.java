package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-08-29
 */
public interface IExamPaperService extends IService<ExamPaper> {
    List<ExamPaperVo> getList();
}
