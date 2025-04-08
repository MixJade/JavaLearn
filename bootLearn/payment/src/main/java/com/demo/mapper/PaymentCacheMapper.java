package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.PaymentCache;
import org.apache.ibatis.annotations.Mapper;

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

    // 清除所有缓存(包括重置计数器)
    void truncateCache();
}
