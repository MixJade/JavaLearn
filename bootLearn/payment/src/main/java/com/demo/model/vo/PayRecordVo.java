package com.demo.model.vo;

import com.demo.model.entity.PaymentRecord;

@SuppressWarnings("unused")
public class PayRecordVo extends PaymentRecord {

    /**
     * 收支类型名称
     */
    private String keyName;

    /**
     * 字典大类,存于代码
     */
    private Integer bigType;

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Integer getBigType() {
        return bigType;
    }

    public void setBigType(Integer bigType) {
        this.bigType = bigType;
    }
}
