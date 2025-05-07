package com.demo.model.chart;

import java.math.BigDecimal;

/**
 * 每个天的收支记录(从数据库查出的,用于柱状图)
 */
public record DayPayBarDo(String label, BigDecimal moneyOut, BigDecimal moneyIn) {
}
