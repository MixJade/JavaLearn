package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 收支缓存表(微信导入)
 * </p>
 *
 * @author MixJade
 * @since 2025-04-30
 */
@TableName("payment_cache")
@SuppressWarnings("unused")
public class PaymentCache implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 缓存主键
     */
    @TableId(value = "cache_id", type = IdType.AUTO)
    private Integer cacheId;

    /**
     * 付费日期
     */
    private LocalDate payDate;

    /**
     * 付费时间
     */
    private String payTime;

    /**
     * 交易类型
     */
    private String payType;

    /**
     * 交易对方
     */
    private String payMan;

    /**
     * 商品名称
     */
    private String wareName;

    /**
     * 1收入0支出
     */
    private Boolean isIncome;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 当前状态
     */
    private String payState;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 是否失效,1是0否
     */
    private Boolean isDel;

    public Integer getCacheId() {
        return cacheId;
    }

    public void setCacheId(Integer cacheId) {
        this.cacheId = cacheId;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayMan() {
        return payMan;
    }

    public void setPayMan(String payMan) {
        this.payMan = payMan;
    }

    public String getWareName() {
        return wareName;
    }

    public void setWareName(String wareName) {
        this.wareName = wareName;
    }

    public Boolean getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(Boolean isIncome) {
        this.isIncome = isIncome;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }
}
