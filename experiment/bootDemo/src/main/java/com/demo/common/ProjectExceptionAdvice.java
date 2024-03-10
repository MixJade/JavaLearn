package com.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ProjectExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ProjectExceptionAdvice.class);

    @ExceptionHandler(DuplicateKeyException.class)//关于添加学生重复插入
    public Result doDuplicateKeyException() {
        return Result.error("不能重复插入");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)//关于空值插入
    public Result doDataIntegrityViolationException(DataIntegrityViolationException e) {
        e.printStackTrace();
        return Result.error("表单不能有空值");
    }

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex) {
        log.error("出现了异常:\n" + ex);
        // 其他异常，没有预计的异常，发送固定消息，安抚客户
        return Result.error("系统繁忙，请稍后再试！");
    }
}
