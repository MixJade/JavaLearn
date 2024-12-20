package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PaymentDictMapper;
import com.demo.model.entity.PaymentDict;
import com.demo.service.IPaymentDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收支类型表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2024-12-20
 */
@Service
public class PaymentDictServiceImpl extends ServiceImpl<PaymentDictMapper, PaymentDict> implements IPaymentDictService {

    @Override
    public List<PaymentDict> getOption() {
        return lambdaQuery()
                .select(PaymentDict::getPaymentType, PaymentDict::getKeyName, PaymentDict::getIsIncome)
                .orderByAsc(PaymentDict::getBigType)
                .list();
    }
}
