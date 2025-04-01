package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.dto.CacheToRecordDto;
import com.demo.model.entity.PaymentCache;
import com.demo.service.IPaymentCacheService;
import com.demo.service.IPaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 收支缓存表(微信导入) 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2025-03-31
 */
@RestController
@RequestMapping("/api/paymentCache")
public class PaymentCacheController {
    private final IPaymentCacheService paymentCacheService;
    private final IPaymentRecordService paymentRecordService;

    @Autowired
    public PaymentCacheController(IPaymentCacheService paymentCacheService, IPaymentRecordService paymentRecordService) {
        this.paymentCacheService = paymentCacheService;
        this.paymentRecordService = paymentRecordService;
    }

    @GetMapping
    public IPage<PaymentCache> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return paymentCacheService.getByPage(pageNum, pageSize);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = paymentCacheService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @DeleteMapping("/delAll")
    public Result delAll() {
        return Result.choice("删除全部", paymentCacheService.delAll());
    }

    @PostMapping
    public Result add(@RequestBody CacheToRecordDto cacheToRecordDto) {
        boolean addRes = paymentRecordService.save(cacheToRecordDto);
        paymentCacheService.lambdaUpdate()
                .set(PaymentCache::getIsDel, true)
                .eq(PaymentCache::getCacheId, cacheToRecordDto.getCacheId())
                .update();
        return Result.choice("添加", addRes);
    }

    /**
     * 上穿并保存csv中的消费数据
     *
     * @param file 上传csv的二进制流
     * @return 保存成功
     */
    @PostMapping("/upload-csv")
    public Result uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return Result.error("请上传一个 CSV 文件");
        return Result.choice("缓存入库", paymentCacheService.saveCsv(file));
    }

    /**
     * 下载一个样例csv
     */
    @GetMapping("/sample-csv")
    public ResponseEntity<byte[]> sampleCsv() {
        // 加载资源文件
        ClassPathResource resource = new ClassPathResource("static/sample/WxPaymentSamples.csv");

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", "WxPaymentSamples.csv");

        // 返回响应实体
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] bytes = inputStream.readAllBytes();
            headers.setContentLength(bytes.length);
            return ResponseEntity.ok().headers(headers).body(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
