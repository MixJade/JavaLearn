package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.ExamPaperDto;
import com.demo.model.entity.ExamPaper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    IPage<ExamPaper> getByPage(IPage<ExamPaper> page, @Param("dto") ExamPaperDto examPaperDto);

    String queryFolderName(Integer id);

    int queryPaperNum(Integer id);
}
