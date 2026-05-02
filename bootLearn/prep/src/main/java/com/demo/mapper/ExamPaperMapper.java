package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 试卷表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-12
 */
@Mapper
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {
    List<ExamPaperVo> getList();
}
