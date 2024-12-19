package com.demo.mapper;

import com.demo.model.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 收支记录表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

}
