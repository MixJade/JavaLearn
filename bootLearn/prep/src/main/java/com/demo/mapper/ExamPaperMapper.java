package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.ExamPaper;
import com.demo.model.entity.ExamQuest;
import com.demo.model.entity.ExamQuestOpt;
import com.demo.model.vo.ExamPaperVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 试卷表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Mapper
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {
    List<ExamPaperVo> getList();

    /**
     * 按试卷查询整卷题目：题目序号升序，序号相同时按主键升序保证顺序稳定
     *
     * @param paperId 试卷主键
     * @return 题目列表，没有题目时为空集合
     */
    List<ExamQuest> getQuestsByPaper(@Param("paperId") Integer paperId);

    /**
     * 按试卷查询整卷题目下的所有选项：靠 exam_quest.paper_id 过滤，省掉「先查题目主键再 in」的一步
     * <p>选项按主键升序返回：保存时是按页面顺序批量插入的，自增主键的递增顺序即页面顺序</p>
     *
     * @param paperId 试卷主键
     * @return 选项列表，没有选项时为空集合
     */
    List<ExamQuestOpt> getOptsByPaper(@Param("paperId") Integer paperId);
}
