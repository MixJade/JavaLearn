package com.demo.model.vo;

import com.demo.model.entity.ExamPaper;

import java.util.List;

/**
 * 试卷详情：试卷本体 + 整卷题目（题目里带各自的选项）
 *
 * @param examPaper 试卷本体，试卷不存在时为 null
 * @param questList 题目列表，按题目序号升序；没有题目时为空集合（不是 null）
 * @since 2026-10-27
 */
public record PaperDetailVo(ExamPaper examPaper, List<QuestAndOptVo> questList) {
}
