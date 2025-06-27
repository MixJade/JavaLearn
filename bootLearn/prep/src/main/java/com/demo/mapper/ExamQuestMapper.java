package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.ExamQuestDto;
import com.demo.model.entity.ExamQuest;
import com.demo.model.vo.QuestImgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 题目表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Mapper
public interface ExamQuestMapper extends BaseMapper<ExamQuest> {
    IPage<ExamQuest> getByPage(IPage<ExamQuest> page, @Param("dto") ExamQuestDto questDto);

    // 通过题目id查询题源id
    Integer queryCateId(Integer questId);

    // 通过题源主键查询图片列表
    List<QuestImgVo> queryImgListByCate(Integer cateId);
}
