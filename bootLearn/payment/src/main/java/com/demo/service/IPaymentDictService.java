package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.PayDictPageDto;
import com.demo.model.entity.PaymentDict;
import com.demo.model.entity.BigType;
import com.demo.model.vo.PaymentDictVo;
import com.demo.model.vo.TypeSelectVo;

import java.util.List;

/**
 * <p>
 * 收支类型表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2024-12-20
 */
public interface IPaymentDictService extends IService<PaymentDict> {

    /**
     * 获取选项值
     *
     * @param isIncome 是否收入
     * @return 收入大类+其下的小类
     */
    List<TypeSelectVo> getOption(Boolean isIncome);

    /**
     * 查询所有
     *
     * @return 收入小类+对应收支数量
     */
    IPage<PaymentDictVo> getByPage(int pageNum, int pageSize, PayDictPageDto payDictPageDto);

    /**
     * 查询所有的大类
     */
    List<BigType> getBigTypes();
}
