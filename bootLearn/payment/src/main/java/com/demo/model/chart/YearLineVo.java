package com.demo.model.chart;

import com.demo.model.dto.YearPayDo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用于年度报告的线性图展示
 *
 * @param yearMoney 当年消费总结
 * @param moneyOut  每月的支出
 * @param moneyIn   每月的收入
 * @param money     每月的盈余
 */
public record YearLineVo(YearPayDo yearMoney, List<BigDecimal> moneyOut, List<BigDecimal> moneyIn,
                         List<BigDecimal> money) {
}
