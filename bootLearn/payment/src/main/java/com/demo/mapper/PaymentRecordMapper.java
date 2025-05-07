package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.model.chart.DayPayVo;
import com.demo.model.chart.MonthPayVo;
import com.demo.model.dto.BigTypePieDo;
import com.demo.model.dto.ChartDo;
import com.demo.model.dto.PayRecordPageDto;
import com.demo.model.dto.YearPayDo;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.PayRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    IPage<PayRecordVo> getByPage(IPage<PayRecordVo> page, @Param("dto") PayRecordPageDto payRecordPageDto);

    List<MonthPayVo> getYearMonthByYear(Integer year);

    YearPayDo getYearMoney(Integer year, Integer month);

    List<DayPayVo> getMonthDayByMonth(Integer year, Integer month);

    List<ChartDo> getPieChart(Integer year, Integer month, Boolean isIncome);

    BigDecimal getYearAvgMonth(Integer year, boolean isIncome);

    // 获取一月某大类的组成(用于环形图)
    List<BigTypePieDo> getBigTypePieByYear(Integer year, Integer month, Integer bigType, Boolean isIncome);

    // 查询对应数据库的数据(可以只指定年)(用于导出)
    List<PaymentRecord> getRecordsByMonth(Integer year, Integer month);
}
