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
import org.springframework.transaction.annotation.Transactional;

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
        // 选项按主键升序返回：保存时是按页面顺序批量插入的，自增主键的递增顺序即页面顺序
        // （顺序依赖这一步，否则数据库返回顺序不定会导致前端回显错位）
        List<ExamQuestOpt> examQuestOpts = examQuestOptService.lambdaQuery()
                .eq(ExamQuestOpt::getQuestId, examQuest.getQuestId())
                .orderByAsc(ExamQuestOpt::getOptId)
                .list();
        return new QuestAndOptVo(examQuest, examQuestOpts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveQuestAll(QuestAndOptVo questAndOptVo) {
        ExamQuest examQuest = questAndOptVo.examQuest();
        if (examQuest == null || examQuest.getQuestId() == null)
            throw new IllegalArgumentException("题目主键不能为空");
        Integer questId = examQuest.getQuestId();

        // 1.按页面内容保存题目主干与解析（quest_content 数据库非空，空值兜底成空串）
        String questContent = examQuest.getQuestContent() == null ? "" : examQuest.getQuestContent();
        String questAnalysis = examQuest.getQuestAnalysis() == null ? "" : examQuest.getQuestAnalysis();
        boolean questSaved = lambdaUpdate()
                .eq(ExamQuest::getQuestId, questId)
                .set(ExamQuest::getQuestContent, questContent)
                .set(ExamQuest::getQuestAnalysis, questAnalysis)
                .update();

        // 2.选项全量覆盖：页面支持增删选项，无法按主键逐个比对，故先按题目清空再按页面顺序重建
        examQuestOptService.lambdaUpdate()
                .eq(ExamQuestOpt::getQuestId, questId)
                .remove();

        List<ExamQuestOpt> optList = questAndOptVo.examQuestOpts();
        if (optList != null && !optList.isEmpty()) {
            for (ExamQuestOpt opt : optList) {
                // 主键交给数据库自增，清理前端可能带过来的旧主键
                opt.setOptId(null);
                opt.setQuestId(questId);
                // opt_cont 数据库非空，缺失时兜底成空串；is_correct 有默认值，这里显式补上
                if (opt.getOptCont() == null)
                    opt.setOptCont("");
                if (opt.getIsCorrect() == null)
                    opt.setIsCorrect(false);
            }
            // 按 list 顺序批量插入，自增主键的递增顺序即为选项顺序
            examQuestOptService.saveBatch(optList);
        }
        return questSaved;
    }
}
