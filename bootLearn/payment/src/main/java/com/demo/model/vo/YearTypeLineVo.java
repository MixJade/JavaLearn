package com.demo.model.vo;

import java.math.BigDecimal;
import java.util.List;

public record YearTypeLineVo(List<BigDecimal> eat,
                             List<BigDecimal> run,
                             List<BigDecimal> home,
                             List<BigDecimal> play,
                             List<BigDecimal> life,
                             List<BigDecimal> buy,
                             List<BigDecimal> salary) {
}
