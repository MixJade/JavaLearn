package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.QuestionOption;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目选项表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Mapper
public interface QuestionOptionMapper extends BaseMapper<QuestionOption> {

}
