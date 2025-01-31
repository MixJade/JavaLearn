package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 每个月份的各类收支记录
 */
@SuppressWarnings("unused")
public class MonthTypePay {
    private Integer month;// 月份
    private BigDecimal eat;// 餐饮
    private BigDecimal run;// 出行
    private BigDecimal home;// 居住
    private BigDecimal play;// 娱乐
    private BigDecimal life;// 日常
    private BigDecimal buy;// 购物
    private BigDecimal salary;// 工资

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public BigDecimal getEat() {
        return eat;
    }

    public void setEat(BigDecimal eat) {
        this.eat = eat;
    }

    public BigDecimal getRun() {
        return run;
    }

    public void setRun(BigDecimal run) {
        this.run = run;
    }

    public BigDecimal getHome() {
        return home;
    }

    public void setHome(BigDecimal home) {
        this.home = home;
    }

    public BigDecimal getPlay() {
        return play;
    }

    public void setPlay(BigDecimal play) {
        this.play = play;
    }

    public BigDecimal getLife() {
        return life;
    }

    public void setLife(BigDecimal life) {
        this.life = life;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public void setBuy(BigDecimal buy) {
        this.buy = buy;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
