package com.demo.model.dto;

/**
 * 前端查询账单记录表所用请求体
 *
 * @since 2025-03-28 11:12:34
 */
public record PayRecordPageDto(Integer bigType, Integer paymentType, String beginDate, String endDate) {
}
