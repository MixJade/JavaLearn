package com.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.model.dto.DayPayData;
import com.demo.model.dto.MonthPayData;
import com.demo.model.dto.PaymentRecordDto;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.ChartVo;

import java.util.List;

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

    /**
     * 获取一年中各个月份的收支总结
     *
     * @param year 年份 2024
     */
    List<MonthPayData> getMonthDataByYear(Integer year);

    /**
     * 获取一月中每天的收支总结
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    List<DayPayData> getDayDataByMonth(Integer year, Integer month);

    /**
     * 获取一月的饼图数据
     *
     * @param year  年份 2024
     * @param month 月份 01
     */
    ChartVo getPieChart(Integer year, Integer month);
}
