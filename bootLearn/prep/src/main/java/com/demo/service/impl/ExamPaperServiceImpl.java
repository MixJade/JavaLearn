package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ExamPaperMapper;
import com.demo.model.entity.ExamPaper;
import com.demo.model.entity.ExamQuest;
import com.demo.model.entity.ExamQuestOpt;
import com.demo.model.vo.ExamPaperVo;
import com.demo.model.vo.PaperDetailVo;
import com.demo.model.vo.QuestAndOptVo;
import com.demo.service.IExamPaperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public PaperDetailVo getPaperDetail(Integer id) {
        // 1.试卷本体：试卷页顶部展示试卷名 / 总分 / 时长
        ExamPaper examPaper = getById(id);
        if (examPaper == null) return new PaperDetailVo(null, List.of());

        // 2.整卷题目：题目序号升序（纸质试卷的展示顺序必须稳定，不能靠数据库默认顺序）
        List<ExamQuest> questList = baseMapper.getQuestsByPaper(id);
        if (questList.isEmpty()) return new PaperDetailVo(examPaper, List.of());

        // 3.整卷选项一次查完
        Map<Integer, List<ExamQuestOpt>> optGroup = baseMapper.getOptsByPaper(id).stream()
                .collect(Collectors.groupingBy(ExamQuestOpt::getQuestId));

        // 4.按题目分组，没有选项的题目给空列表
        List<QuestAndOptVo> questAndOptList = questList.stream()
                .map(quest -> new QuestAndOptVo(quest, optGroup.getOrDefault(quest.getQuestId(), List.of())))
                .toList();
        return new PaperDetailVo(examPaper, questAndOptList);
    }
}
