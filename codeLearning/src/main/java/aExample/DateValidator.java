package aExample;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

/**
 * 校验一个字符串是否是MM-dd格式
 *
 * @since 2025-07-04 15:41:43
 */
public class DateValidator {
    private static final Pattern DATE_PATTERN = Pattern.compile(
            "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"
    );

    public static boolean isValidDateFormat(String dateStr) {
        // 1. 验证字符串格式
        if (!DATE_PATTERN.matcher(dateStr).matches()) {
            return false;
        }

        // 2. 验证日期有效性（使用2024年作为闰年基准）
        try {
            // 拼接一个闰年年份进行验证
            LocalDate date = LocalDate.parse("2024-" + dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 检查解析后的月份和日期是否与输入一致
            String[] parts = dateStr.split("-");
            int inputMonth = Integer.parseInt(parts[0]);
            int inputDay = Integer.parseInt(parts[1]);

            return date.getMonthValue() == inputMonth && date.getDayOfMonth() == inputDay;
        } catch (DateTimeException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isValidDateFormat("02-29")); // true (2024年是闰年)
        System.out.println(isValidDateFormat("02-30")); // false (2月没有30日)
        System.out.println(isValidDateFormat("13-01")); // false (月份超出范围)
        System.out.println(isValidDateFormat("05-05")); // true
        System.out.println(isValidDateFormat("5-5"));   // false (格式不正确)
    }
}