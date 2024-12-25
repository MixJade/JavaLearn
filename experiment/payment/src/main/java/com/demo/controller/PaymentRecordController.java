package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.DayPayData;
import com.demo.model.dto.MonthPayData;
import com.demo.model.dto.PaymentRecordDto;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.ChartVo;
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
    @GetMapping("/month")
    public List<MonthPayData> getMonthDataByYear(Integer year) {
        return paymentRecordService.getMonthDataByYear(year);
    }

    /**
     * 获取一月中每天的收支总结
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/day")
    public List<DayPayData> getDayDataByMonth(Integer year, Integer month) {
        return paymentRecordService.getDayDataByMonth(year, month);
    }

    /**
     * 获取一月的饼图数据
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/pieChart")
    public ChartVo getPieChart(Integer year, Integer month) {
        return paymentRecordService.getPieChart(year, month);
    }
}
