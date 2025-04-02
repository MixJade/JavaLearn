package com.demo.model.chart;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图表展示类
 */
public record ChartVo(List<Integer> bigTypes, List<String> labels, List<String> colors, List<BigDecimal> moneys) {
}
