package com.demo.controller;


import com.demo.model.chart.*;
import com.demo.model.dto.YearPayDo;
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
     * (柱状图)获取一月每天的收支总结
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/barDay")
    public DayPayBarVo barDay(Integer year, Integer month) {
        return paymentRecordService.barDay(year, month);
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
     * 获取一年中某大类的具体情况(用于圆环图)
     *
     * @param year     年份 2024
     * @param bigType  大类id
     * @param isIncome 收/支
     */
    @GetMapping("/bigTypePie")
    public ChartVo getBigTypePieByYear(Integer year, Integer bigType, Boolean isIncome) {
        return paymentRecordService.getBigTypePieByYear(year, 0, bigType, isIncome);
    }

    /**
     * 获取一月中不同大类收/支情况(用于饼、柱图)
     *
     * @param year     年份 2024
     * @param month    月份 01
     * @param isIncome 收入/支出
     */
    @GetMapping("/monthPie")
    public ChartVo getMonthPie(Integer year, Integer month, Boolean isIncome) {
        return paymentRecordService.getPieChart(year, month, isIncome);
    }

    /**
     * 获取一月的收支总结(用于月度报告)
     *
     * @param year  年份 2024
     * @param month 月份 1
     */
    @GetMapping("/monthMoney")
    public YearPayDo getMonthMoney(Integer year, Integer month) {
        return paymentRecordService.getMonthMoney(year, month);
    }

    /**
     * 获取一月中某大类的具体情况(用于圆环图)
     *
     * @param year     年份 2024
     * @param month    月份 01
     * @param bigType  大类id
     * @param isIncome 收/支
     */
    @GetMapping("/bigTypePieMonth")
    public ChartVo getBigTypePieByMonth(Integer year, Integer month, Integer bigType, Boolean isIncome) {
        return paymentRecordService.getBigTypePieByYear(year, month, bigType, isIncome);
    }
}
