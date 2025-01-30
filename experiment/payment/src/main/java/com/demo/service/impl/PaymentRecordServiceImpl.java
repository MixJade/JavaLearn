package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.common.BigTypeData;
import com.demo.mapper.PaymentRecordMapper;
import com.demo.model.dto.*;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.ChartVo;
import com.demo.model.vo.YearLineVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<MonthPayData> getYearMonthByYear(Integer year) {
        List<MonthPayData> monthDataByYear = baseMapper.getYearMonthByYear(year);
        for (MonthPayData mpd : monthDataByYear) {
            mpd.setMoney(mpd.getMoneyIn().subtract(mpd.getMoneyOut()));
        }
        return monthDataByYear;
    }

    @Override
    public YearLineVo getYearLineByYear(Integer year) {
        List<MonthPayData> monthDataByYear = baseMapper.getYearMonthByYear(year);
        List<BigDecimal> moneyOut = new ArrayList<>(),
                moneyIn = new ArrayList<>(),
                money = new ArrayList<>();
        // 拆分成三条数据线
        for (MonthPayData mpd : monthDataByYear) {
            moneyOut.add(mpd.getMoneyOut());
            moneyIn.add(mpd.getMoneyIn());
            money.add(mpd.getMoneyIn().subtract(mpd.getMoneyOut()));
        }
        return new YearLineVo(moneyOut, moneyIn, money);
    }

    @Override
    public YearPayData getYearMoney(Integer year) {
        // 一年的总收入、支出
        YearPayData ym = baseMapper.getYearMoney(year);
        ym.setMoney(ym.getMoneyIn().subtract(ym.getMoneyOut()));
        // 一年的月平均消费
        BigDecimal monthAvgMoneyIn = baseMapper.getYearAvgMonth(year, true);
        BigDecimal monthAvgMoneyOut = baseMapper.getYearAvgMonth(year, false);
        ym.setMonthAvgMoneyIn(monthAvgMoneyIn);
        ym.setMonthAvgMoneyOut(monthAvgMoneyOut);
        ym.setMonthAvgMoney(monthAvgMoneyIn.subtract(monthAvgMoneyOut));
        // 一年的食宿支出
        BigDecimal lifeMoney = baseMapper.getYearLifeMoney(year);
        ym.setLifeMoney(lifeMoney);
        // 劳动回报比(总收入/支出)
        ym.setWorkRatio(ym.getMoneyIn().divide(ym.getMoneyOut(), 2, RoundingMode.HALF_UP));
        // 食宿花费比例
        ym.setLifeRatio(lifeMoney.divide(ym.getMoneyOut(), 2, RoundingMode.HALF_UP));
        // 平均食宿花费
        BigDecimal payDayCount = baseMapper.getPayDayCount(year);
        ym.setLifeDayPay(lifeMoney.divide(payDayCount, 2, RoundingMode.HALF_UP));
        return ym;
    }

    @Override
    public List<DayPayData> getMonthDayByMonth(Integer year, Integer month) {
        return baseMapper.getMonthDayByMonth(year, month);
    }

    @Override
    public ChartVo getPieChart(Integer year, Integer month) {
        List<ChartDo> pieChart = baseMapper.getPieChart(year, month);
        // 转变为前端能直接用的数据结构
        List<String> labels = new ArrayList<>(),
                colors = new ArrayList<>();
        List<BigDecimal> moneys = new ArrayList<>();
        // 获取大类map
        Map<Integer, String> bigTypeMap = BigTypeData.getMap();
        // 组装大类数据
        for (ChartDo chartDo : pieChart) {
            labels.add(bigTypeMap.get(chartDo.getBigType()));
            colors.add(BigTypeData.getBigTypeColor(chartDo.getBigType()));
            moneys.add(chartDo.getMoney());
        }
        // 查询当月总支出
        BigDecimal outMoney = baseMapper.getPayByMonth(year, month);
        return new ChartVo(labels, colors, moneys, outMoney);
    }
}
