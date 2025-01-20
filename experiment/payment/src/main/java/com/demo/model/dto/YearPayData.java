package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 某年的收支记录
 */
@SuppressWarnings("unused")
public class YearPayData {
    private BigDecimal moneyOut;// 支出
    private BigDecimal moneyIn;// 收入
    private BigDecimal money;// 总收益

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
