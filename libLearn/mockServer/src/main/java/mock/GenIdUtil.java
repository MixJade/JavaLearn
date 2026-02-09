package mock;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 生成一个ID，用于替换“此处自动生成”文本
 *
 * @since 2026-02-09 10:36:48
 */
public class GenIdUtil {
    // 定义时间格式化器
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String genId() {
        // 取当前系统时间并格式化, 拼接 "0"
        return LocalDateTime.now().format(formatter) + "0";
    }
}
