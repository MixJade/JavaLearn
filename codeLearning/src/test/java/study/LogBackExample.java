package study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackExample {
    private static final Logger log = LoggerFactory.getLogger(LogBackExample.class);

    public static void main(String[] args) {
        log.debug("----------------------------");
        log.debug("散玉的暑假生活");
        log.info("第一次的日志尝试");
        permissive();
    }

    private static void permissive() {
        int a = 10;
        int b = 2;
        int c = 0;
        try {
            log.info("开始Try尝试");
            log.trace("a={} b={} c={}", a, b, c);
            log.trace("a+b={}", (a + b));
            System.out.println(b / c);
        } catch (Exception e) {
            log.error("功能出现异常" + e);
        }
    }
}
