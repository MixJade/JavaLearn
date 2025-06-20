package com.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.entity.ExamQuestOpt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目选项表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Mapper
public interface ExamQuestOptMapper extends BaseMapper<ExamQuestOpt> {
    IPage<ExamQuestOpt> getByPage(IPage<ExamQuestOpt> page);
}
