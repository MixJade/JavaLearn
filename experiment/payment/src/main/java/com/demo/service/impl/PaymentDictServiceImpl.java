package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.BigTypeData;
import com.demo.mapper.PaymentDictMapper;
import com.demo.model.entity.PaymentDict;
import com.demo.model.vo.TypeOption;
import com.demo.service.IPaymentDictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<TypeOption> getOption(boolean isIncome) {
        List<PaymentDict> paymentDictList = lambdaQuery()
                .orderByAsc(PaymentDict::getBigType)
                .eq(PaymentDict::getIsIncome, isIncome)
                .list();
        Map<Integer, String> bigTypeMap = BigTypeData.getMap();
        Map<Integer, List<PaymentDict>> paymentDictMap = new HashMap<>();

        // 直接在循环中进行分组和大类-小类的组装
        List<TypeOption> typeOptions = new ArrayList<>();
        for (PaymentDict paymentDict : paymentDictList) {
            int bigType = paymentDict.getBigType();
            // 如果不存在这个key，则添加到Map，然后无论前面key是否存在，为对应value加值
            paymentDictMap.computeIfAbsent(bigType, k -> new ArrayList<>()).add(paymentDict);
            // 只在第一次遇到大类时创建 TypeOption 然后放入列表的引用
            if (paymentDictMap.get(bigType).size() == 1) {
                typeOptions.add(new TypeOption(bigTypeMap.get(bigType), paymentDictMap.get(bigType)));
            }
        }
        return typeOptions;
    }
}
