package com.demo.controller;


import com.demo.model.chart.ChartVo;
import com.demo.model.chart.DayPayVo;
import com.demo.model.chart.MonthPayVo;
import com.demo.model.chart.YearLineVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 图表专用 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-03-24
 */
@RestController
@RequestMapping("/api/chart")
public class ChartController {
    private final IPaymentRecordService paymentRecordService;

    @Autowired
    public ChartController(IPaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    /**
     * (日历图)获取一年各月的收支总结
     *
     * @param year 年份 2024
     * @return 12个月的情况(3月1组, 共4组)
     */
    @GetMapping("/calendarMonth")
    public List<List<MonthPayVo>> calendarMonth(Integer year) {
        return paymentRecordService.calendarMonth(year);
    }

    /**
     * (日历图)获取一月每天的收支总结
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/calendarDay")
    public List<List<DayPayVo>> calendarDay(Integer year, Integer month) {
        return paymentRecordService.calendarDay(year, month);
    }

    /**
     * 获取一年中各个月份的收支总结(用于线形图)
     *
     * @param year 年份 2024
     */
    @GetMapping("/yearLine")
    public YearLineVo getYearLineByYear(Integer year) {
        return paymentRecordService.getYearLineByYear(year);
    }

    /**
     * 获取一年中不同大类收/支情况(用于饼、柱图)
     *
     * @param year     年份 2024
     * @param isIncome 收/支
     */
    @GetMapping("/yearPie")
    public ChartVo getYearPieByYear(Integer year, Boolean isIncome) {
        return paymentRecordService.getPieChart(year, 0, isIncome);
    }

    /**
     * 获取一月的饼图数据
     *
     * @param year     年份 2024
     * @param month    月份 01
     * @param isIncome 收入/支出
     */
    @GetMapping("/monthPie")
    public ChartVo getMonthPie(Integer year, Integer month, Boolean isIncome) {
        return paymentRecordService.getPieChart(year, month, isIncome);
    }
}
