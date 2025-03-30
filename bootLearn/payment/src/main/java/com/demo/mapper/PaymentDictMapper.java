package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.model.entity.PaymentDict;
import com.demo.model.vo.PaymentDictVo;
import com.demo.model.vo.TypeSelectTwoVo;
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

    /**
     * 查询支付类型页面所用,联查数据数量
     *
     * @param bigType 大类,为null查全部
     * @return 支付类型页面数据
     */
    List<PaymentDictVo> getAllByBigType(Integer bigType);


    /**
     * 获取所有的支付类型(二级下拉框所用)
     *
     * @param isIncome 收支类型,为null则查全部
     * @return 所有的二级下拉框数据
     */
    List<TypeSelectTwoVo> getSelectTwoVos(Boolean isIncome);
}
