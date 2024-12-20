package com.demo.service;

import com.demo.model.entity.PaymentDict;
import com.baomidou.mybatisplus.extension.service.IService;

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

    // 获取选项值
    List<PaymentDict> getOption();
}
