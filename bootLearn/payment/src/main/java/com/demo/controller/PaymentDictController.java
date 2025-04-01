package com.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.common.BigTypeData;
import com.demo.common.Result;
import com.demo.model.dto.PayDictPageDto;
import com.demo.model.entity.PaymentDict;
import com.demo.model.vo.*;
import com.demo.service.IPaymentDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 收支类型表 前端控制器
 * </p>
 *
 * @author MixJade
 * @since 2024-12-20
 */
@RestController
@RequestMapping("/api/paymentDict")
public class PaymentDictController {
    private final IPaymentDictService paymentDictService;

    @Autowired
    public PaymentDictController(IPaymentDictService iPaymentDictService) {
        this.paymentDictService = iPaymentDictService;
    }


    @PostMapping
    public Result add(@RequestBody PaymentDict paymentDict) {
        boolean addRes = paymentDictService.save(paymentDict);
        return Result.choice("添加", addRes);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean deleteRes = paymentDictService.removeById(id);
        return Result.choice("删除", deleteRes);
    }

    @PutMapping
    public Result update(@RequestBody PaymentDict paymentDict) {
        boolean updateRes = paymentDictService.updateById(paymentDict);
        return Result.choice("修改", updateRes);
    }

    @PostMapping("/page")
    public IPage<PaymentDictVo> getPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestBody PayDictPageDto payDictPageDto) {
        return paymentDictService.getByPage(pageNum, pageSize, payDictPageDto);
    }

    @GetMapping("/bigType")
    public List<BigType> getBigType() {
        return BigTypeData.getBigTypes();
    }

    /**
     * records页面表单的二级下拉框选项
     *
     * @return 二级下拉框数据(按收支区分)
     */
    @GetMapping("/twoOption")
    public TwoTypeOptVo getTwoOption() {
        return new TwoTypeOptVo(
                paymentDictService.getOption(true),
                paymentDictService.getOption(false)
        );
    }

    /**
     * records页面查询二级下拉框选项
     *
     * @return 二级下拉框数据
     */
    @GetMapping("/option")
    public List<TypeSelectVo> getOption() {
        return paymentDictService.getOption(null);
    }

}
