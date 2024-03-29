package com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@ComponentScan("com.controller")
//开启json数据类型自动转换
@EnableWebMvc
public class SpringMvcConfig {
}
