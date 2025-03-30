package com.demo.model.chart;

import java.math.BigDecimal;

/**
 * 每个天的收支记录
 */
@SuppressWarnings("unused")
public class DayPayVo {
    private final String payDate;// 付费日期,yyyyMMdd格式
    private final boolean currentMonth;// 是否当前月
    private int dayNum; // 天数
    private final Integer payCount;// 交易次数
    private final BigDecimal moneyOut;// 支出
    private final BigDecimal moneyIn;// 收入

    public DayPayVo(String payDate, Integer payCount, BigDecimal moneyOut, BigDecimal moneyIn) {
        this.payDate = payDate;
        this.currentMonth = true;
        this.payCount = payCount;
        this.moneyOut = moneyOut;
        this.moneyIn = moneyIn;
    }

    public DayPayVo(String payDate,int dayNum, boolean currentMonth) {
        this.payDate = payDate;
        this.currentMonth = currentMonth;
        this.dayNum = dayNum;
        this.payCount = 0;
        this.moneyOut = BigDecimal.valueOf(0);
        this.moneyIn = BigDecimal.valueOf(0);
    }

    public String getPayDate() {
        return payDate;
    }

    public boolean isCurrentMonth() {
        return currentMonth;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public BigDecimal getMoneyOut() {
        return moneyOut;
    }

    public BigDecimal getMoneyIn() {
        return moneyIn;
    }
}
