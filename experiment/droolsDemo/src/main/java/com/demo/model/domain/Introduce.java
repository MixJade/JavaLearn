package com.demo.model.domain;

import lombok.Data;

@Data
public class Introduce {
    boolean sex;
    Integer age;
    // 返回结果：这个只用于drools设置返回值,会在响应时取出
    String result;
}
