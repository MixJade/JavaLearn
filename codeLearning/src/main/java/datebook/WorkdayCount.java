package datebook;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * 计算两个日期中的工作日天数
 *
 * @since 2025-11-10 16:45:42
 */
public class WorkdayCount {
    // 2025年法定节假日（含调休补班，补班日需标记为工作日）
    // 节假日：放假当天；补班日：原本周末但需上班，需从"非工作日"中排除
    private static final Set<LocalDate> HOLIDAYS_2025 = new HashSet<>();
    private static final Set<LocalDate> MAKEUP_WORKDAYS_2025 = new HashSet<>();

    static {
        // 1. 2025年法定节假日（根据企业微信整理）
        addHolidays("2025-01-01"); // 元旦：1.1
        addHolidays("2025-01-27", "2025-02-05"); // 春节：1.27-2.05
        addHolidays("2025-04-04"); // 清明节：4.4
        addHolidays("2025-05-01", "2025-05-05"); // 劳动节：5.1-5.5
        addHolidays("2025-06-02"); // 端午节：6.2
        addHolidays("2025-10-01", "2025-10-08"); // 国庆节：10.1-10.8

        // 2. 2025年调休补班日（原本是周末，需上班）
        addWorkdays("2025-01-25"); // 周六（补春节假期）
        addWorkdays("2025-01-26"); // 周日（补春节假期）
        addWorkdays("2025-02-08"); // 周六（补春节假期）
        addWorkdays("2025-02-15"); // 周六（补春节假期）
        addWorkdays("2025-04-27"); // 周日（补劳动节假期）
        addWorkdays("2025-05-11"); // 周六（补劳动节假期）
        addWorkdays("2025-09-28"); // 周日（补国庆节假期）
        addWorkdays("2025-10-11"); // 周六（补国庆节假期）
    }

    // 批量添加节假日
    private static void addHolidays(String oneDay) {
        HOLIDAYS_2025.add(LocalDate.parse(oneDay));
    }

    // 批量添加节假日
    private static void addHolidays(String start, String end) {
        LocalDate endDate = LocalDate.parse(end);
        LocalDate current = LocalDate.parse(start);
        // 遍历从 start 到 end 的所有日期（包括首尾）
        while (current.isBefore(endDate.plusDays(1))) {
            HOLIDAYS_2025.add(current);
            // 日期+1
            current = current.plusDays(1);
        }
    }

    // 批量添加调休补班日
    private static void addWorkdays(String oneDay) {
        MAKEUP_WORKDAYS_2025.add(LocalDate.parse(oneDay));
    }

    /**
     * 计算两个日期之间的工作日数（含开始日期，不含结束日期？可调整）
     *
     * @param start 开始日期（如 2025-02-17）
     * @param end   结束日期（如 2025-11-10）
     * @return 工作日总数
     */
    public static long calculateWorkdays(LocalDate start, LocalDate end) {
        // 校验日期合法性：开始日期必须在结束日期之前
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("开始日期不能晚于结束日期");
        }

        long workdays = 0;
        LocalDate current = start;

        // 遍历从 start 到 end 的所有日期（含 start，含 end：用 isBefore(end.plusDays(1))）
        while (current.isBefore(end.plusDays(1))) {
            // 判断是否为工作日：
            // 1. 不是周末（周六/周日），且 2. 不是法定节假日，或 3. 是调休补班日
            boolean isWeekend = current.getDayOfWeek() == DayOfWeek.SATURDAY
                    || current.getDayOfWeek() == DayOfWeek.SUNDAY;
            boolean isHoliday = HOLIDAYS_2025.contains(current);
            boolean isMakeupWorkday = MAKEUP_WORKDAYS_2025.contains(current);
            if ((!isWeekend && !isHoliday) || isMakeupWorkday) {
                workdays++;
            }
            // 日期+1
            current = current.plusDays(1);
        }

        return workdays;
    }

    // 测试：计算 2025-02-17 到 2025-11-10 的工作日
    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2025, 2, 17);
        LocalDate end = LocalDate.of(2025, 11, 10);

        long workdays = calculateWorkdays(start, end);
        System.out.printf("2025-02-17 到 2025-11-10 的工作日数：%d 天%n", workdays);
        // 输出结果：184 天
    }
}