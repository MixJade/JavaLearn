package com.demo.model.vo;

import com.demo.model.entity.PaymentDict;

import java.util.List;

/**
 * 使用时的支付分类
 *
 * @param bigType         大类编号
 * @param typeName        大类名称
 * @param paymentDictList 大类的支付小类
 */
public record TypeOption(Integer bigType, String typeName, List<PaymentDict> paymentDictList) {
}
