package study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogBackExample.class);

    public static void main(String[] args) {
        LOGGER.debug("----------------------------");
        LOGGER.debug("散玉的暑假生活");
        LOGGER.info("第一次的日志尝试");
        permissive();
    }

    private static void permissive() {
        int a = 10;
        int b = 2;
        int c = 0;
        try {
            LOGGER.info("开始Try尝试");
            LOGGER.trace("a={} b={} c={}", a, b, c);
            LOGGER.trace("a+b={}", (a + b));
            System.out.println(b / c);
        } catch (Exception e) {
            LOGGER.error("功能出现异常" + e);
        }
    }
}
