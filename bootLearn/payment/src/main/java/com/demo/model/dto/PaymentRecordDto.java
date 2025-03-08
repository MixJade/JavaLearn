package com.demo.model.dto;

import com.demo.model.entity.PaymentRecord;

@SuppressWarnings("unused")
public class PaymentRecordDto extends PaymentRecord {

    /**
     * 收支类型名称
     */
    private String keyName;

    /**
     * 分类颜色
     */
    private String color;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
