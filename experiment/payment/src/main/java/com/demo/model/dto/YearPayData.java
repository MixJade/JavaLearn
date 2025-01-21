package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 某年的收支记录
 */
@SuppressWarnings("unused")
public class YearPayData {
    private BigDecimal moneyOut;// 总支出
    private BigDecimal moneyIn;// 总收入
    private BigDecimal money;// 总收益
    private BigDecimal monthAvgMoneyIn;// 月平均收入
    private BigDecimal monthAvgMoneyOut;// 月平均支出
    private BigDecimal monthAvgMoney;// 月平均收益
    private BigDecimal lifeMoney;// 一年的食宿总支出
    private BigDecimal workRatio;// 劳动回报比(总收入/支出)
    private BigDecimal lifeRatio;// 食宿花费占总消费比例
    private BigDecimal lifeDayPay;// 平均每天食宿花费
    private BigDecimal lifeDay;// 当前食宿水平可持续天数

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

    public BigDecimal getLifeMoney() {
        return lifeMoney;
    }

    public void setLifeMoney(BigDecimal lifeMoney) {
        this.lifeMoney = lifeMoney;
    }

    public BigDecimal getWorkRatio() {
        return workRatio;
    }

    public void setWorkRatio(BigDecimal workRatio) {
        this.workRatio = workRatio;
    }

    public BigDecimal getLifeRatio() {
        return lifeRatio;
    }

    public void setLifeRatio(BigDecimal lifeRatio) {
        this.lifeRatio = lifeRatio;
    }

    public BigDecimal getLifeDayPay() {
        return lifeDayPay;
    }

    public void setLifeDayPay(BigDecimal lifeDayPay) {
        this.lifeDayPay = lifeDayPay;
    }

    public BigDecimal getLifeDay() {
        return lifeDay;
    }

    public void setLifeDay(BigDecimal lifeDay) {
        this.lifeDay = lifeDay;
    }
}
