package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 图表展示类
 */
@SuppressWarnings("unused")
public class ChartDo {
    private Integer paymentType;

    /**
     * 类型名称
     */
    private String keyName;

    /**
     * 分类颜色
     */
    private String color;

    private BigDecimal money;

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
