package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 每个月份的收支记录
 */
@SuppressWarnings("unused")
public class MonthPayData {
    private Integer month;// 月份
    private BigDecimal moneyOut;// 支出
    private BigDecimal moneyIn;// 收入

    private BigDecimal money;// 盈余

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getMoneyOut() {
        return moneyOut;
    }

    public void setMoneyOut(BigDecimal moneyOut) {
        this.moneyOut = moneyOut;
    }

    public BigDecimal getMoneyIn() {
        return moneyIn;
    }

    public void setMoneyIn(BigDecimal moneyIn) {
        this.moneyIn = moneyIn;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
