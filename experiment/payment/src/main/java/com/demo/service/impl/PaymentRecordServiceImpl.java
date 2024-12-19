package com.demo.service.impl;

import com.demo.model.entity.PaymentRecord;
import com.demo.mapper.PaymentRecordMapper;
import com.demo.service.IPaymentRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收支记录表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2024-12-19
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements IPaymentRecordService {

}
