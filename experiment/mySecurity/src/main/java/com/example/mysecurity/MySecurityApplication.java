package com.example.mysecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySecurityApplication {
    private static final Logger log = LoggerFactory.getLogger(com.example.mysecurity.MySecurityApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(com.example.mysecurity.MySecurityApplication.class, args);
        log.info("登录地址：http://localhost:8080");
        log.info("登录地址2：http://localhost:8080/user/login.html");
    }
}
