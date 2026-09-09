package com.demo.model.vo;

import com.demo.model.entity.ExamQuest;
import com.demo.model.entity.ExamQuestOpt;

import java.util.List;

/**
 * 通过题目编号查询题目详情
 *
 * @since 2026-09-09 16:45:20
 */
public record QuestAndOptVo(ExamQuest examQuest, List<ExamQuestOpt> examQuestOpts) {
}
