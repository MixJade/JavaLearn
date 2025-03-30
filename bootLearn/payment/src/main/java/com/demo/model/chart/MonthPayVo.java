package com.demo.model.chart;

import java.math.BigDecimal;

/**
 * 每个月份的收支记录
 */
@SuppressWarnings("unused")
public class MonthPayVo {
    private Integer month;// 月份
    private BigDecimal moneyOut;// 支出
    private BigDecimal moneyIn;// 收入

    private BigDecimal money;// 盈余

    public MonthPayVo() {
    }

    public MonthPayVo(Integer month) {
        this.month = month;
        this.moneyOut = BigDecimal.valueOf(0);
        this.moneyIn = BigDecimal.valueOf(0);
        this.money = BigDecimal.valueOf(0);
    }

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
