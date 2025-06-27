package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.ExamQuestMapper;
import com.demo.model.dto.ExamQuestDto;
import com.demo.model.entity.ExamQuest;
import com.demo.model.vo.QuestImgListVo;
import com.demo.model.vo.QuestImgVo;
import com.demo.service.IExamQuestService;
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
    @Override
    public IPage<ExamQuest> getByPage(int pageNum, int pageSize, ExamQuestDto questDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), questDto);
    }

    @Override
    public QuestImgListVo getCateImg(Integer questId) {
        Integer cateId = baseMapper.queryCateId(questId);
        List<QuestImgVo> questImgVoList = baseMapper.queryImgListByCate(cateId);
        return new QuestImgListVo(cateId, questImgVoList);
    }
}
