package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.Result;
import com.demo.model.entity.PaymentCache;
import com.demo.service.IPaymentCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    public PaymentCacheController(IPaymentCacheService paymentCacheService) {
        this.paymentCacheService = paymentCacheService;
    }

    @GetMapping
    public IPage<PaymentCache> getPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        return paymentCacheService.getByPage(pageNum, pageSize);
    }

    @PostMapping("/upload-csv")
    public Result uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return Result.error("请上传一个 CSV 文件");
        return Result.choice("缓存入库", paymentCacheService.saveCsv(file));
    }
}
