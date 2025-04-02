package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 某年的收支记录
 */
@SuppressWarnings("unused")
public class YearPayDo {
    private BigDecimal moneyOut;// 总支出
    private BigDecimal moneyIn;// 总收入
    private BigDecimal money;// 盈余
    private BigDecimal monthAvgMoneyIn;// 月均收入
    private BigDecimal monthAvgMoneyOut;// 月均支出
    private BigDecimal monthAvgMoney;// 月均盈余

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

    public BigDecimal getMonthAvgMoneyIn() {
        return monthAvgMoneyIn;
    }

    public void setMonthAvgMoneyIn(BigDecimal monthAvgMoneyIn) {
        this.monthAvgMoneyIn = monthAvgMoneyIn;
    }

    public BigDecimal getMonthAvgMoneyOut() {
        return monthAvgMoneyOut;
    }

    public void setMonthAvgMoneyOut(BigDecimal monthAvgMoneyOut) {
        this.monthAvgMoneyOut = monthAvgMoneyOut;
    }

    public BigDecimal getMonthAvgMoney() {
        return monthAvgMoney;
    }

    public void setMonthAvgMoney(BigDecimal monthAvgMoney) {
        this.monthAvgMoney = monthAvgMoney;
    }
}
