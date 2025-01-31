package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.DayPayData;
import com.demo.model.dto.MonthPayData;
import com.demo.model.dto.PaymentRecordDto;
import com.demo.model.dto.YearPayData;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.ChartVo;
import com.demo.model.vo.YearLineVo;
import com.demo.model.vo.YearTypeLineVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收支记录表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
@RestController
@RequestMapping("/paymentRecord")
public class PaymentRecordController {

    private final IPaymentRecordService paymentRecordService;

    @Autowired
    public PaymentRecordController(IPaymentRecordService paymentRecordService) {
        this.paymentRecordService = paymentRecordService;
    }

    @PostMapping
    public Result add(@RequestBody PaymentRecord paymentRecord) {
        boolean addRes = paymentRecordService.save(paymentRecord);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = paymentRecordService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody PaymentRecord paymentRecord) {
        boolean updateRes = paymentRecordService.updateById(paymentRecord);
        return Result.choice("修改", updateRes);
    }

    @GetMapping
    public IPage<PaymentRecordDto> getAll(int pageNum, int pageSize, Integer bigType, String beginDate, String endDate) {
        return paymentRecordService.getByPage(pageNum, pageSize, bigType, beginDate, endDate);
    }

    @GetMapping("/{id}")
    public PaymentRecord getById(@PathVariable Integer id) {
        return paymentRecordService.getById(id);
    }

    /**
     * 获取一年中各个月份的收支总结
     *
     * @param year 年份 2024
     */
    @GetMapping("/yearMonth")
    public List<MonthPayData> getYearMonthByYear(Integer year) {
        return paymentRecordService.getYearMonthByYear(year);
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
     * 获取一年中每月各种类型支出(用于线形图)
     *
     * @param year 年份 2024
     */
    @GetMapping("/yearTypeLLine")
    public YearTypeLineVo getYearTypeLineInteger(Integer year) {
        return paymentRecordService.getYearTypeLineInteger(year);
    }

    /**
     * 获取一年中各个月份的消费成分(用于饼状图)
     *
     * @param year 年份 2024
     */
    @GetMapping("/yearPie")
    public ChartVo getYearPieByYear(Integer year) {
        return paymentRecordService.getPieChart(year, 0);
    }

    /**
     * 获取一年的收支总结
     *
     * @param year 年份 2024
     */
    @GetMapping("/yearMoney")
    public YearPayData getYearMoney(Integer year) {
        return paymentRecordService.getYearMoney(year);
    }


    /**
     * 获取一月中每天的收支总结
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/monthDay")
    public List<DayPayData> getMonthDayByMonth(Integer year, Integer month) {
        return paymentRecordService.getMonthDayByMonth(year, month);
    }

    /**
     * 获取一月的饼图数据
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/monthPie")
    public ChartVo getMonthPie(Integer year, Integer month) {
        return paymentRecordService.getPieChart(year, month);
    }
}
