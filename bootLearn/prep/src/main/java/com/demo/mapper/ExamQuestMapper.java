package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.ExamQuest;
import org.apache.ibatis.annotations.Mapper;

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
    // 通过题目id查询题源id
    Integer queryCateId(Integer questId);

    // 通过题源主键查询图片列表
    List<Integer> queryImgListByCate(Integer cateId);
}
