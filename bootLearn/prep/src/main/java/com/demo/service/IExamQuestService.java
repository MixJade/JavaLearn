package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.entity.ExamQuest;
import com.demo.model.vo.QuestAndOptVo;
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
    /**
     * 根据id查询图源文件夹ID
     *
     * @param questId
     * @return
     */
    QuestImgListVo getCateImg(Integer questId);

    boolean updQuest(ExamQuest examQuest);

    QuestAndOptVo getView(Integer id);

    /**
     * 保存题目详情：题目主干 + 题目解析 + 选项（选项按页面顺序全量覆盖）
     *
     * @param questAndOptVo 题目详情，含题目实体与选项列表
     * @return 题目主干/解析是否更新成功
     */
    boolean saveQuestAll(QuestAndOptVo questAndOptVo);
}
