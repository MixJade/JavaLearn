package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(BootDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BootDemoApplication.class, args);
        log.info("启动完成,项目地址:http://localhost:8080/");
    }

}
