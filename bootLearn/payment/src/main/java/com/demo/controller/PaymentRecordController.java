package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.PayRecordPageDto;
import com.demo.model.vo.PayRecordVo;
import com.demo.model.dto.YearPayData;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.chart.ChartVo;
import com.demo.model.chart.YearLineVo;
import com.demo.model.chart.YearTypeLineVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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

    @PostMapping("/page")
    public IPage<PayRecordVo> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody PayRecordPageDto payRecordPageDto) {
        return paymentRecordService.getByPage(pageNum, pageSize, payRecordPageDto);
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
        String fileName = "paymentRecord(" + year + (month == 0 ? "" : "-" + month) + ").sql";
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
