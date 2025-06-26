package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.entity.ExamPaper;
import com.demo.model.vo.ExamPaperVo;
import org.apache.ibatis.annotations.Mapper;

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
    IPage<ExamPaperVo> getByPage(IPage<ExamPaper> page);

    String queryFolderName(Integer id);

    int queryPaperNum(Integer id);
}
