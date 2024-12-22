package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.dto.PaymentDictDto;
import com.demo.model.entity.PaymentDict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 收支类型表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2024-12-20
 */
@Mapper
public interface PaymentDictMapper extends BaseMapper<PaymentDict> {

    List<PaymentDictDto> getAllByBigType(Integer bigType);
}
