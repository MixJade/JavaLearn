package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.entity.PaymentRecord;
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
    public List<PaymentRecord> getAll() {
        return paymentRecordService.list();
    }

    @GetMapping("/{id}")
    public PaymentRecord getById(@PathVariable Integer id) {
        return paymentRecordService.getById(id);
    }
}
