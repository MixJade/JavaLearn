package com.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger3Config {
    @Bean
    public OpenAPI springShopOpenAPI() {
        // 项目证书
        License license = new License()
                .name("MIT")
                .url("https://opensource.org/licenses/MIT");

        // 作者
        Contact contact = new Contact()
                .name("MixJade")
                .url("https://mixjade.github.io/")
                .email("2231973602@qq.com");

        // 项目的基础描述
        Info info = new Info()
                .title("测试Drools")
                .description("测试Drools和Swagger的在线接口文档")
                .version("v1.0.0")
                .contact(contact)
                .license(license);

        // 额外描述
        ExternalDocumentation externalDocumentation = new ExternalDocumentation()
                .description("这是一个额外的描述。")
                .url("https://mixjade.github.io/");

        return new OpenAPI()
                .info(info)
                .externalDocs(externalDocumentation);
    }
}
