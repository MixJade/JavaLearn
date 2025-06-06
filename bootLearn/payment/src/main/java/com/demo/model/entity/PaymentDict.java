package com.demo.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 收支类型表
 * </p>
 *
 * @author MixJade
 * @since 2024-12-23
 */
@TableName("payment_dict")
@SuppressWarnings("unused")
public class PaymentDict implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId(value = "payment_type", type = IdType.AUTO)
    private Integer paymentType;

    /**
     * 类型名称
     */
    private String keyName;

    /**
     * 1收入0支出
     */
    private Boolean isIncome;

    /**
     * 字典大类,存于代码
     */
    private Integer bigType;

    /**
     * 分类颜色
     */
    private String color;

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Boolean getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(Boolean isIncome) {
        this.isIncome = isIncome;
    }

    public Integer getBigType() {
        return bigType;
    }

    public void setBigType(Integer bigType) {
        this.bigType = bigType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
