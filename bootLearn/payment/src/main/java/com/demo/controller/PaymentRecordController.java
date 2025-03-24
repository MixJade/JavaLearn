package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.DayPayData;
import com.demo.model.dto.PaymentRecordDto;
import com.demo.model.dto.YearPayData;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.ChartVo;
import com.demo.model.vo.MonthPayVo;
import com.demo.model.vo.YearLineVo;
import com.demo.model.vo.YearTypeLineVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
@RequestMapping("/api/paymentRecord")
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
    public IPage<PaymentRecordDto> getAll(int pageNum, int pageSize, Integer bigType, Integer paymentType, String beginDate, String endDate) {
        return paymentRecordService.getByPage(pageNum, pageSize, bigType, paymentType, beginDate, endDate);
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
    @Deprecated
    @GetMapping("/yearMonth")
    public List<MonthPayVo> getYearMonthByYear(Integer year) {
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

    /**
     * 下载一月的sql
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    @GetMapping("/downInsertSql")
    public ResponseEntity<byte[]> downInsertSql(Integer year, Integer month) {
        // 要放入文件的字符串
        String insertSql = paymentRecordService.generateInsertSql(year, month);
        // 将字符串转换为字节数组
        byte[] fileContent = insertSql.getBytes(StandardCharsets.UTF_8);
        // 文件名称
        String fileName = "消费数据(" + year + "年" + (month == 0 ? "" : month + "月") + ").sql";
        // 对文件名进行 URL 编码
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(fileContent.length);
        // 返回响应实体
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
