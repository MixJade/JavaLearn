package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PaymentRecordMapper;
import com.demo.model.dto.DayPayData;
import com.demo.model.dto.MonthPayData;
import com.demo.model.dto.PaymentRecordDto;
import com.demo.model.entity.PaymentRecord;
import com.demo.service.IPaymentRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public IPage<PaymentRecordDto> getByPage(int pageNum, int pageSize, Integer bigType, String beginDate, String endDate) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), bigType, beginDate, endDate);
    }

    @Override
    public List<MonthPayData> getMonthDataByYear(Integer year) {
        List<MonthPayData> monthDataByYear = baseMapper.getMonthDataByYear(year);
        for (MonthPayData mpd : monthDataByYear) {
            mpd.setMoney(mpd.getMoneyIn().subtract(mpd.getMoneyOut()));
        }
        return monthDataByYear;
    }

    @Override
    public List<DayPayData> getDayDataByMonth(Integer year, Integer month) {
        return baseMapper.getDayDataByMonth(year, month);
    }
}
