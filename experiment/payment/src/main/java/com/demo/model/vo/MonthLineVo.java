package com.demo.model.vo;

import java.math.BigDecimal;
import java.util.List;

public record MonthLineVo(List<BigDecimal> moneyOut, List<BigDecimal> moneyIn, List<BigDecimal> money) {
}
