package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentApplication {
    private static final Logger log = LoggerFactory.getLogger(PaymentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
        log.info("启动完成,本地访问: http://localhost:23043/");
    }

}
