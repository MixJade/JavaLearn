package datebook;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

/**
 * 计算两个日期间的月份数
 *
 * @since 2025-12-29 13:49:03
 */
public class MonthCount {
    public static void main(String[] args) {
        // 1. 定义两个日期（2023-03-29 和 2026-01-01）
        LocalDate startDate = LocalDate.of(2023, 3, 29);
        LocalDate endDate = LocalDate.of(2026, 1, 1);

        // 2. 校验日期顺序（确保结束日期晚于开始日期）
        if (endDate.isBefore(startDate)) {
            LocalDate temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        // 3. 计算完整月份数
        long fullMonths = calculateFullMonths(startDate, endDate);

        // 4. 计算剩余天数（基于完整月份差，得到开始日期偏移后的日期，再计算与结束日期的天数差）
        LocalDate dateAfterFullMonths = startDate.plusMonths(fullMonths);
        long remainingDays = ChronoUnit.DAYS.between(dateAfterFullMonths, endDate);

        // 5. 输出结果
        System.out.printf("开始日期：%s，结束日期：%s%n", startDate, endDate);
        System.out.printf("完整月份数：%d 个月%n", fullMonths);
        System.out.printf("剩余天数：%d 天%n", remainingDays);
    }

    /**
     * 计算两个日期之间的完整月份数（精准处理月末日期，如3月29日→4月29日为1个月）
     */
    private static long calculateFullMonths(LocalDate start, LocalDate end) {
        // 通过YearMonth计算
        YearMonth startYm = YearMonth.from(start);
        YearMonth endYm = YearMonth.from(end);
        long monthsByYm = startYm.until(endYm, ChronoUnit.MONTHS);

        // 校验：如果结束日期的日 < 开始日期的日，说明最后一个月不完整，需要减1
        if (end.getDayOfMonth() < start.getDayOfMonth()) {
            monthsByYm--;
        }

        return monthsByYm;
    }
}