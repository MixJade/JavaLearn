package com.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.PaymentDictDto;
import com.demo.model.entity.PaymentDict;
import com.demo.model.vo.TypeOption;

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
    List<TypeOption> getOption(boolean isIncome);

    /**
     * 查询所有
     *
     * @param bigType 大类
     * @return 收入小类+对应收支数量
     */
    List<PaymentDictDto> getAllByBigType(Integer bigType);
}
