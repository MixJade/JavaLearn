package com.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.entity.QuestionRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目记录表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Mapper
public interface QuestionRecordMapper extends BaseMapper<QuestionRecord> {
    IPage<QuestionRecord> getByPage(IPage<QuestionRecord> page);
}
