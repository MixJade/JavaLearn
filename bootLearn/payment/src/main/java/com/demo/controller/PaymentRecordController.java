package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.PayRecordPageDto;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.PayRecordVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
     * 下载一年的sql
     *
     * @param year 年份 2024
     */
    @GetMapping("/downInsertSql")
    public ResponseEntity<byte[]> downInsertSql(Integer year) {
        // 要放入文件的字符串
        String insertSql = paymentRecordService.generateInsertSql(year);
        // 将字符串转换为字节数组
        byte[] fileContent = insertSql.getBytes(StandardCharsets.UTF_8);
        // 文件名称
        String fileName = "paymentRecord(" + year + ").sql";
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

    /**
     * 上穿并保存csv中的消费数据
     *
     * @param file 上传csv的二进制流
     * @return 保存成功
     */
    @PostMapping("/upload-sql")
    public Result uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return Result.error("请上传一个SQL文件");
        String sqlCont = readMultipartFileContent(file);
        String expectedStart = "insert into payment_record (record_id, payment_type, is_income, money, pay_date, remark)";
        if (sqlCont.trim().toLowerCase().startsWith(expectedStart.toLowerCase())) {
            return Result.choice("SQL导入", paymentRecordService.runSqlStr(sqlCont));
        } else {
            return Result.error("上传sql文件不合规范");
        }
    }

    private String readMultipartFileContent(MultipartFile file) {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = file.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}