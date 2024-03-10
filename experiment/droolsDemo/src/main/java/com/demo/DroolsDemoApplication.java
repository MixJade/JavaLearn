package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DroolsDemoApplication {
    private static final Logger log = LoggerFactory.getLogger(DroolsDemoApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(DroolsDemoApplication.class, args);
        log.info("启动完成,项目地址:http://localhost:8080/");
        log.info("接口文档(旧ui):http://localhost:8080/swagger-ui/index.html");
        log.info("接口文档:http://localhost:8080/doc.html");
    }

}
