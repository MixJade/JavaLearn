package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.InetAddress;

@SpringBootApplication
public class PrepApplication {
    private static final Logger log = LoggerFactory.getLogger(PrepApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PrepApplication.class, args);
        log.info("本地IP: http://localhost:23046/");
        try {
            InetAddress ip01 = InetAddress.getLocalHost();
            //noinspection HttpUrlsUsage
            log.info("局域IP: http://{}:23046", ip01.getHostAddress());

            // 假定当前服务只在window系统运行,在启动之后默认打开浏览器(服务端请注释)
            // Runtime.getRuntime().exec("cmd /c start http://localhost:23041");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
