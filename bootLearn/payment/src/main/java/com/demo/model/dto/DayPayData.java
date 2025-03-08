package com.demo.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每个天的收支记录
 */
@SuppressWarnings("unused")
public class DayPayData {
    private LocalDate payDate;// 付费日期
    private Integer payCount;// 交易次数
    private BigDecimal moneyOut;// 支出
    private BigDecimal moneyIn;// 收入

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
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
}
