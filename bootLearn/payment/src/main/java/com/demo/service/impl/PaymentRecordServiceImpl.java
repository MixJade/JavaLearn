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
import com.demo.model.vo.YearTypeLineVo;
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
    public IPage<PaymentRecordDto> getByPage(int pageNum, int pageSize, Integer bigType, Integer paymentType, String beginDate, String endDate) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), bigType, paymentType, beginDate, endDate);
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
    public YearTypeLineVo getYearTypeLineInteger(Integer year) {
        List<MonthTypePay> monthTypePays = baseMapper.getYearTypeMonth(year);
        List<BigDecimal> eat = new ArrayList<>(),
                run = new ArrayList<>(),
                home = new ArrayList<>(),
                play = new ArrayList<>(),
                life = new ArrayList<>(),
                buy = new ArrayList<>(),
                salary = new ArrayList<>();
        // 拆分成三条数据线
        for (MonthTypePay mpd : monthTypePays) {
            eat.add(mpd.getEat());
            run.add(mpd.getRun());
            home.add(mpd.getHome());
            play.add(mpd.getPlay());
            life.add(mpd.getLife());
            buy.add(mpd.getBuy());
            salary.add(mpd.getSalary());
        }
        return new YearTypeLineVo(eat, run, home, play, life, buy, salary);
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
        List<Integer> bigTypes = new ArrayList<>();
        List<String> labels = new ArrayList<>(),
                colors = new ArrayList<>();
        List<BigDecimal> moneys = new ArrayList<>();
        // 获取大类map
        Map<Integer, String> bigTypeMap = BigTypeData.getMap();
        Map<Integer, String> bigTypeColorMap = BigTypeData.getColorMap();
        // 组装大类数据
        for (ChartDo chartDo : pieChart) {
            bigTypes.add(chartDo.getBigType());
            labels.add(bigTypeMap.get(chartDo.getBigType()));
            colors.add(bigTypeColorMap.get(chartDo.getBigType()));
            moneys.add(chartDo.getMoney());
        }
        // 查询当月总支出
        BigDecimal outMoney = baseMapper.getPayByMonth(year, month);
        return new ChartVo(bigTypes, labels, colors, moneys, outMoney);
    }

    @Override
    public String generateInsertSql(Integer year, Integer month) {
        // 查询数据库中的所有数据
        List<PaymentRecord> paymentRecords = baseMapper.getRecordsByMonth(year, month);

        if (paymentRecords.isEmpty()) {
            return "";
        }

        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("insert into payment_record (record_id, payment_type, is_income, money, pay_date, remark)")
                .append("\nvalues");

        for (int i = 0; i < paymentRecords.size(); i++) {
            PaymentRecord pr = paymentRecords.get(i);
            // 定义每行数据的模板字符串
            String rowTemplate = "(%d, %d, %d, %.2f, '%s', '%s')";
            // 格式化每行数据
            String rowData = String.format(rowTemplate,
                    pr.getRecordId(),
                    pr.getPaymentType(),
                    pr.getIsIncome() ? 1 : 0,
                    pr.getMoney(),
                    pr.getPayDate(),
                    pr.getRemark());

            insertStatement.append(rowData);
            // 行尾字符串
            if (i < paymentRecords.size() - 1)
                insertStatement.append(",\n       ");
            else insertStatement.append(";");
        }
        return insertStatement.toString();
    }
}
