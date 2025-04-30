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
 * 收支记录表
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
@TableName("payment_record")
@SuppressWarnings("unused")
public class PaymentRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 记录主键
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 收支类型
     */
    private Integer paymentType;

    /**
     * 1收入0支出
     */
    private Boolean isIncome;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 付费时间
     */
    private LocalDate payDate;

    /**
     * 备注
     */
    private String remark;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Boolean getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(Boolean isIncome) {
        this.isIncome = isIncome;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
