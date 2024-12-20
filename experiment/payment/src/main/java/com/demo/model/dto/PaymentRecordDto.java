package com.demo.model.dto;

import com.demo.model.entity.PaymentRecord;

@SuppressWarnings("unused")
public class PaymentRecordDto extends PaymentRecord {

    /**
     * 收支类型名称
     */
    private String keyName;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
