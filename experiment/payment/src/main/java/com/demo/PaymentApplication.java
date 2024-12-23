package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class PaymentApplication {
    private static final Logger log = LoggerFactory.getLogger(PaymentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
        log.info("本地IP: http://localhost:23043/");
        try {
            InetAddress ip01 = InetAddress.getLocalHost();
            //noinspection HttpUrlsUsage
            log.info("局域IP: http://{}:23043", ip01.getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
