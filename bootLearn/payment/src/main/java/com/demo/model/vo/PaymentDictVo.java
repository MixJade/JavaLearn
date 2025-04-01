package com.demo.model.vo;

import com.demo.model.entity.PaymentDict;

/**
 * 类型字典查询所用
 */
@SuppressWarnings("unused")
public class PaymentDictVo extends PaymentDict {
    /**
     * 对应记录数量
     */
    Integer recordNum;
    /**
     * 大类名称
     */
    private String bigTypeName;
    /**
     * 大类颜色
     */
    private String bigTypeColor;

    public Integer getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Integer recordNum) {
        this.recordNum = recordNum;
    }

    public String getBigTypeName() {
        return bigTypeName;
    }

    public void setBigTypeName(String bigTypeName) {
        this.bigTypeName = bigTypeName;
    }

    public String getBigTypeColor() {
        return bigTypeColor;
    }

    public void setBigTypeColor(String bigTypeColor) {
        this.bigTypeColor = bigTypeColor;
    }
}
