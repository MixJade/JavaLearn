package com.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ExamQuestMapper;
import com.demo.model.entity.ExamQuest;
import com.demo.model.entity.ExamQuestOpt;
import com.demo.model.vo.QuestAndOptVo;
import com.demo.model.vo.QuestImgListVo;
import com.demo.service.IExamQuestOptService;
import com.demo.service.IExamQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Service
public class ExamQuestServiceImpl extends ServiceImpl<ExamQuestMapper, ExamQuest> implements IExamQuestService {
    private final IExamQuestOptService examQuestOptService;

    @Autowired
    public ExamQuestServiceImpl(IExamQuestOptService examQuestOptService) {
        this.examQuestOptService = examQuestOptService;
    }

    @Override
    public QuestImgListVo getCateImg(Integer questId) {
        Integer cateId = baseMapper.queryCateId(questId);
        List<Integer> imageIds = baseMapper.queryImgListByCate(cateId);
        return new QuestImgListVo(cateId, imageIds);
    }

    @Override
    public boolean updQuest(ExamQuest examQuest) {
        return lambdaUpdate().eq(ExamQuest::getQuestId, examQuest.getQuestId())
                .set(StringUtils.isNotEmpty(examQuest.getQuestContent()), ExamQuest::getQuestContent, examQuest.getQuestContent())
                .set(StringUtils.isNotEmpty(examQuest.getQuestAnalysis()), ExamQuest::getQuestAnalysis, examQuest.getQuestAnalysis())
                .update();
    }

    @Override
    public QuestAndOptVo getView(Integer id) {
        ExamQuest examQuest = getById(id);
        List<ExamQuestOpt> examQuestOpts = examQuestOptService.lambdaQuery()
                .eq(ExamQuestOpt::getQuestId, examQuest.getQuestId())
                .list();
        return new QuestAndOptVo(examQuest, examQuestOpts);
    }
}
