package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.ExamQuestDto;
import com.demo.model.entity.ExamQuest;
import com.demo.model.vo.QuestImgListVo;

/**
 * <p>
 * 题目表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
public interface IExamQuestService extends IService<ExamQuest> {
    IPage<ExamQuest> getByPage(int pageNum, int pageSize, ExamQuestDto questDto);

    /**
     * 根据id查询图源文件夹ID
     * @param questId
     * @return
     */
    QuestImgListVo getCateImg(Integer questId);
}
