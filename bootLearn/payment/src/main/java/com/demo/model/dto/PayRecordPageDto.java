package com.demo.model.dto;

/**
 * 前端查询账单记录表所用请求体
 *
 * @since 2025-03-28 11:12:34
 */
@SuppressWarnings("unused")
public class PayRecordPageDto {
    Integer bigType;
    Integer paymentType;
    String beginDate;
    String endDate;

    public Integer getBigType() {
        return bigType;
    }

    public void setBigType(Integer bigType) {
        this.bigType = bigType;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
