package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 题目表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
