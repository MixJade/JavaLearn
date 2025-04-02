package com.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.PaymentRecordMapper;
import com.demo.model.chart.ChartVo;
import com.demo.model.chart.DayPayVo;
import com.demo.model.chart.MonthPayVo;
import com.demo.model.chart.YearLineVo;
import com.demo.model.dto.BigTypePieDo;
import com.demo.model.dto.ChartDo;
import com.demo.model.dto.PayRecordPageDto;
import com.demo.model.dto.YearPayDo;
import com.demo.model.entity.PaymentRecord;
import com.demo.model.vo.PayRecordVo;
import com.demo.service.IPaymentRecordService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    public IPage<PayRecordVo> getByPage(int pageNum, int pageSize, PayRecordPageDto payRecordPageDto) {
        return baseMapper.getByPage(new Page<>(pageNum, pageSize), payRecordPageDto);
    }

    @Override
    public List<List<MonthPayVo>> calendarMonth(Integer year) {
        List<MonthPayVo> monthPayVos = baseMapper.getYearMonthByYear(year);
        for (MonthPayVo mpd : monthPayVos) {
            mpd.setMoney(mpd.getMoneyIn().subtract(mpd.getMoneyOut()));
        }
        // 将长度补齐到12
        if (monthPayVos.size() < 12) {
            for (int i = monthPayVos.size() + 1; i < 13; i++) {
                monthPayVos.add(new MonthPayVo(i));
            }
        }

        // 将列表转换为二维列表，每个子列表长度为 3
        List<List<MonthPayVo>> result = new ArrayList<>(4);
        for (int i = 0; i < 12; i += 3) {
            result.add(monthPayVos.subList(i, i + 3));
        }
        return result;
    }

    @Override
    public YearLineVo getYearLineByYear(Integer year) {
        List<MonthPayVo> monthDataByYear = baseMapper.getYearMonthByYear(year);
        List<BigDecimal> moneyOut = new ArrayList<>(),
                moneyIn = new ArrayList<>(),
                money = new ArrayList<>();
        // 拆分成三条数据线
        for (MonthPayVo mpd : monthDataByYear) {
            moneyOut.add(mpd.getMoneyOut());
            moneyIn.add(mpd.getMoneyIn());
            money.add(mpd.getMoneyIn().subtract(mpd.getMoneyOut()));
        }

        // 附录：当年总收支、月份平均收支
        YearPayDo yearMoney = getYearMoney(year);
        return new YearLineVo(yearMoney, moneyOut, moneyIn, money);
    }

    /**
     * 获取一年的收支总结
     *
     * @param year 年份 2024
     */
    private YearPayDo getYearMoney(Integer year) {
        // 一年的总收入、支出
        YearPayDo ym = baseMapper.getYearMoney(year);
        ym.setMoney(ym.getMoneyIn().subtract(ym.getMoneyOut()));
        // 一年的月平均消费
        BigDecimal monthAvgMoneyIn = baseMapper.getYearAvgMonth(year, true);
        BigDecimal monthAvgMoneyOut = baseMapper.getYearAvgMonth(year, false);
        ym.setMonthAvgMoneyIn(monthAvgMoneyIn);
        ym.setMonthAvgMoneyOut(monthAvgMoneyOut);
        ym.setMonthAvgMoney(monthAvgMoneyIn.subtract(monthAvgMoneyOut));
        return ym;
    }

    @Override
    public List<List<DayPayVo>> calendarDay(Integer year, Integer month) {
        // 从数据库查询,转为map
        List<DayPayVo> dayByMonth = baseMapper.getMonthDayByMonth(year, month);
        Map<String, DayPayVo> payDataMap = new HashMap<>(dayByMonth.size());
        for (DayPayVo payData : dayByMonth) {
            payDataMap.put(payData.getPayDate(), payData);
        }
        // 创建当月1日的LocalDate对象
        LocalDate date = LocalDate.of(year, month, 1);
        // 获取该日期对应的星期几
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // 将 DayOfWeek 的值转换为所需的数字表示（星期一返回 0，星期天返回 6）
        int dayNumber = (dayOfWeek.getValue() + 6) % 7;


        // 循环42次，组装一个六行七列的数据
        List<DayPayVo> payDataList = new ArrayList<>(42);

        // 获取上一个月的数字
        LocalDate minusMonths = date.minusMonths(1);
        int preMonth = minusMonths.getMonthValue();
        int preYear = minusMonths.getYear();
        // 获取上一个月的天数(+1为了循环)
        int preMonthDayNum = minusMonths.lengthOfMonth() + 1;
        // 组装上一个月的天数
        if (dayNumber != 0)
            for (int i = preMonthDayNum - dayNumber; i < preMonthDayNum; i++) {
                payDataList.add(new DayPayVo(String.format("%d-%02d-%02d", preYear, preMonth, i), i, false));
            }

        // 获取当月天数
        int nowMonthDayNum = date.lengthOfMonth();
        // 组装当月天数
        int monthIndex = dayNumber + nowMonthDayNum;
        for (int i = 1; i < nowMonthDayNum + 1; i++) {
            String dayStr = String.format("%d-%02d-%02d", year, month, i);
            if (payDataMap.containsKey(dayStr)) {
                DayPayVo payVo = payDataMap.get(dayStr);
                payVo.setDayNum(i);
                payDataList.add(payVo);
            } else payDataList.add(new DayPayVo(dayStr, i, true));
        }

        // 获取下一个月的数字
        int nextMonth = date.plusMonths(1).getMonthValue();
        int nextYear = date.plusMonths(1).getYear();
        // 组装下月天数
        for (int i = 1; i < 43 - monthIndex; i++) {
            payDataList.add(new DayPayVo(String.format("%d-%02d-%02d", nextYear, nextMonth, i), i, false));
        }
        // 转为6行7列的列表
        List<List<DayPayVo>> result = new ArrayList<>(6);
        int resIndex = 0;
        for (int i = 0; i < 6; i++) {
            List<DayPayVo> res_i = new ArrayList<>(7);
            for (int j = 0; j < 7; j++) {
                res_i.add(payDataList.get(resIndex));
                resIndex++;
            }
            result.add(res_i);
        }
        return result;
    }

    @Override
    public ChartVo getPieChart(Integer year, Integer month, Boolean isIncome) {
        List<ChartDo> pieChart = baseMapper.getPieChart(year, month, isIncome);
        // 转变为前端能直接用的数据结构
        List<Integer> bigTypes = new ArrayList<>();
        List<String> labels = new ArrayList<>(),
                colors = new ArrayList<>();
        List<BigDecimal> moneys = new ArrayList<>();
        // 组装大类数据
        for (ChartDo chartDo : pieChart) {
            bigTypes.add(chartDo.getBigType());
            labels.add(chartDo.getBigTypeName());
            colors.add(chartDo.getBigTypeColor());
            moneys.add(chartDo.getMoney());
        }
        return new ChartVo(bigTypes, labels, colors, moneys);
    }

    @Override
    public ChartVo getBigTypePieByYear(Integer year, Integer month, Integer bigType, Boolean isIncome) {
        List<BigTypePieDo> bigTypePieDos = baseMapper.getBigTypePieByYear(year, month, bigType, isIncome);
        // 转变为前端能直接用的数据结构
        List<Integer> bigTypes = new ArrayList<>();
        List<String> labels = new ArrayList<>(),
                colors = new ArrayList<>();
        List<BigDecimal> moneys = new ArrayList<>();
        // 组装大类数据
        for (BigTypePieDo chartDo : bigTypePieDos) {
            bigTypes.add(chartDo.paymentType());
            labels.add(chartDo.keyName());
            colors.add("#ccc");
            moneys.add(chartDo.money());
        }
        return new ChartVo(bigTypes, labels, colors, moneys);
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
