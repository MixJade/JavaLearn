package datebook;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

/**
 * 测试使用日期工具
 *
 * @since 2024-8-2 21:32:56
 */
public class TestDateUtil {
    /**
     * 测试通过Calendar类来获取当月有多少天，以及哪天是周六、周日
     */
    @Test
    public void testCalendar() {

        Calendar calendar = Calendar.getInstance();

        // 得到当月的总天数
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("本月总天数：" + daysInMonth);

        // 遍历每一天，看看哪些是周六，哪些是周日
        for (int day = 1; day <= daysInMonth; day++) {
            // 将日历设置为当前月和当前天
            calendar.set(Calendar.DAY_OF_MONTH, day);

            // 获取当前天的星期
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // 如果是周六或者周日，打印出来
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                System.out.println(day + "号是" + (dayOfWeek == Calendar.SATURDAY ? "周六" : "周日"));
            }
        }
    }

    /**
     * 测试通过LocalDate类来获取当月有多少天，以及哪天是周六、周日
     */
    @Test
    public void testLocalDate() {
        LocalDate now_date = LocalDate.now();
        System.out.println("当前月的日：" + now_date.getDayOfMonth());
        int lengthOfMonth = now_date.lengthOfMonth();
        System.out.println("当前月的天数：" + lengthOfMonth);

        // 获取当月第一天
        LocalDate firstDay = now_date.with(TemporalAdjusters.firstDayOfMonth());
        // 遍历每一天，看看哪些是周六，哪些是周日
        for (int day = 0; day < lengthOfMonth; day++) {
            // 天数加一后的星期
            LocalDate plusDays = firstDay.plusDays(day);
            DayOfWeek dayOfWeek = plusDays.getDayOfWeek();
            // 如果是周六或者周日，打印出来
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                System.out.println(plusDays.getDayOfMonth() + "号是" + (dayOfWeek == DayOfWeek.SATURDAY ? "周六" : "周日"));
            }
        }
    }
}
