package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.chart.ChartVo;
import com.demo.model.chart.DayPayVo;
import com.demo.model.chart.MonthPayVo;
import com.demo.model.chart.YearLineVo;
import com.demo.model.dto.PayRecordPageDto;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.PayRecordVo;

import java.util.List;

/**
 * <p>
 * 收支记录表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {
    IPage<PayRecordVo> getByPage(int pageNum, int pageSize, PayRecordPageDto payRecordPageDto);

    /**
     * (日历图)获取各月份的收支总结
     *
     * @param year 年份 2024
     * @return 12个月的情况(3月1组, 共4组)
     */
    List<List<MonthPayVo>> calendarMonth(Integer year);

    /**
     * 获取一年中各个月份的收支总结(用于线形图)
     *
     * @param year 年份 2024
     */
    YearLineVo getYearLineByYear(Integer year);

    /**
     * (日历图)获取一月每天的收支总结
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    List<List<DayPayVo>> calendarDay(Integer year, Integer month);

    /**
     * 获取一月的饼图数据
     *
     * @param year     年份 2024
     * @param month    月份 01 (传0视为查询一年的饼图数据)
     * @param isIncome 收入/支出
     */
    ChartVo getPieChart(Integer year, Integer month, Boolean isIncome);

    /**
     * 获取一月的sql
     *
     * @param year  年份 2024
     * @param month 月份 01 (传0视为查询一年数据)
     */
    String generateInsertSql(Integer year, Integer month);
}
