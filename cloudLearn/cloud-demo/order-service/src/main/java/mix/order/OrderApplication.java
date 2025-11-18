package mix.order;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("mix.order.mapper")
@SpringBootApplication
@EnableDiscoveryClient // 开启Nacos服务注册
@EnableFeignClients // 使用Feign
public class OrderApplication {
    private static final Logger log = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        log.info("订单服务启动，端口号：20232");
    }

}