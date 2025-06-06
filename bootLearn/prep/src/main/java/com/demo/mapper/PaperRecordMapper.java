package com.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.entity.PaperRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 试卷记录表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Mapper
public interface PaperRecordMapper extends BaseMapper<PaperRecord> {
    IPage<PaperRecord> getByPage(IPage<PaperRecord> page);
}
