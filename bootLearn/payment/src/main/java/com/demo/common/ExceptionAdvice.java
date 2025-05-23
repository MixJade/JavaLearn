package com.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex) {
        log.error("出现了异常:\n" + ex);
        // 其他异常，没有预计的异常，发送固定消息，安抚客户
        return Result.error("系统繁忙，请稍后再试！");
    }
}
