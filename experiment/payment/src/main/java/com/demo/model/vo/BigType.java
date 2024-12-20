package com.demo.model.vo;

import com.demo.model.entity.PaymentDict;

import java.util.List;

public class BigType {
    private final int typeKey;
    private final String typeName;

    private List<PaymentDict> paymentDictList;

    public BigType(int typeKey, String typeName) {
        this.typeKey = typeKey;
        this.typeName = typeName;
    }

    public int getTypeKey() {
        return typeKey;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<PaymentDict> getPaymentDictList() {
        return paymentDictList;
    }

    public void setPaymentDictList(List<PaymentDict> paymentDictList) {
        this.paymentDictList = paymentDictList;
    }
}
