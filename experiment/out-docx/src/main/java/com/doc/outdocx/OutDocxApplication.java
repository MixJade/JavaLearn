package com.doc.outdocx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * aspose-words测试启动类
 *
 * @since 2023年7月7日 21:11
 */
@SpringBootApplication
public class OutDocxApplication {
    private static final Logger logger = LoggerFactory.getLogger(OutDocxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OutDocxApplication.class, args);
        logger.info("http://localhost:8080/");
    }
}
