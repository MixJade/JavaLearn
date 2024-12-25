package com.demo.model.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 图表展示类
 */
public record ChartVo(List<String> labels, List<String> colors, List<BigDecimal> moneys) {
}
