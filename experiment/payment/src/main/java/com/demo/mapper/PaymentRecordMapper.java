package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.dto.*;
import com.demo.model.entity.PaymentRecord;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 收支记录表 Mapper 接口
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

    IPage<PaymentRecordDto> getByPage(IPage<PaymentRecordDto> page, Integer bigType, String beginDate, String endDate);

    List<MonthPayData> getYearMonthByYear(Integer year);
    List<MonthTypePay> getYearTypeMonth(Integer year);
    YearPayData getYearMoney(Integer year);

    List<DayPayData> getMonthDayByMonth(Integer year, Integer month);

    List<ChartDo> getPieChart(Integer year, Integer month);

    BigDecimal getPayByMonth(Integer year, Integer month);

    BigDecimal getYearAvgMonth(Integer year, boolean isIncome);

    BigDecimal getYearLifeMoney(Integer year);
    BigDecimal getPayDayCount(Integer year);

    // 查询对应数据库的数据(可以只指定年)
    List<PaymentRecord> getRecordsByMonth(Integer year, Integer month);
}
