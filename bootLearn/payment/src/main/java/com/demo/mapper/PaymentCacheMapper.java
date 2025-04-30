package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.PayCachePageDto;
import com.demo.model.entity.PaymentCache;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 收支缓存表(微信导入) Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2025-03-31
 */
@Mapper
public interface PaymentCacheMapper extends BaseMapper<PaymentCache> {
    // 分页查询
    IPage<PaymentCache> getByPage(IPage<PaymentCache> page, @Param("dto") PayCachePageDto payCachePageDto);

    // 清除所有缓存(包括重置计数器)
    void truncateCache();

}
