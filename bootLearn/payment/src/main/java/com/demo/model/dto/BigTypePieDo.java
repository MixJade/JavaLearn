package com.demo.model.dto;

import java.math.BigDecimal;

/**
 * 某大类的环形图数据
 */
public record BigTypePieDo(Integer paymentType, String keyName, BigDecimal money) {
}
