package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.PaymentRecordDto;
import com.demo.model.entity.PaymentRecord;

/**
 * <p>
 * 收支记录表 服务类
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

    IPage<PaymentRecordDto> getByPage(int pageNum, int pageSize, Integer bigType, String beginDate, String endDate);
}
