package com.demo.model.dto;

import com.demo.model.entity.PaymentDict;

@SuppressWarnings("unused")
public class PaymentDictDto extends PaymentDict {
    Integer recordNum;

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }
}