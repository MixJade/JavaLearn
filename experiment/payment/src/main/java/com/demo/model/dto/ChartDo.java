package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 图表展示类
 */
@SuppressWarnings("unused")
public class ChartDo {
    private Integer bigType;
    private BigDecimal money;

    public Integer getBigType() {
        return bigType;
    }

    public void setBigType(Integer bigType) {
        this.bigType = bigType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
