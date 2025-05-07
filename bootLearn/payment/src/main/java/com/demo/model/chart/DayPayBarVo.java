package com.demo.model.chart;

import java.math.BigDecimal;
import java.util.List;

/**
 * 每个天的收支记录(用于柱状图)
 */
public record DayPayBarVo(List<String> labels, List<BigDecimal> moneyOuts, List<BigDecimal> moneyIns) {
}
