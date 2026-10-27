package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;
import com.demo.model.vo.PaperDetailVo;

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

    /**
     * 查询试卷详情：试卷本体 + 整卷题目（每题带选项）
     * <p>试卷页顶部信息与纸面题目一次查回，前端只需发一次请求</p>
     *
     * @param id 试卷主键
     * @return 试卷详情，试卷不存在时 examPaper 为 null、questList 为空集合
     */
    PaperDetailVo getPaperDetail(Integer id);
}
