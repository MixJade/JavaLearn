package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.model.dto.PayDictPageDto;
import com.demo.model.entity.PaymentDict;
import com.demo.model.entity.BigType;
import com.demo.model.vo.PaymentDictVo;
import com.demo.model.vo.TypeSelectTwoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 获取所有的支付类型(二级下拉框所用)
     *
     * @param isIncome 收支类型,为null则查全部
     * @return 所有的二级下拉框数据
     */
    List<TypeSelectTwoVo> getSelectTwoVos(Boolean isIncome);

    /**
     * 查询支付类型页面所用,联查数据数量
     *
     * @return 支付类型页面数据
     */
    IPage<PaymentDictVo> getByPage(Page<Object> page, @Param("dto") PayDictPageDto payDictPageDto);

    /**
     * 查询所有的大类
     */
    List<BigType> getBigTypes();
}
