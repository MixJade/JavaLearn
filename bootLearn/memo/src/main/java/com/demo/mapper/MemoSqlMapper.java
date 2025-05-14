package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.MemoPageDto;
import com.demo.model.entity.MemoSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 备忘sql表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-05-14
 */
@Mapper
public interface MemoSqlMapper extends BaseMapper<MemoSql> {
    IPage<MemoSql> getByPage(IPage<MemoSql> objectPage, @Param("dto") MemoPageDto memoPageDto);
}
