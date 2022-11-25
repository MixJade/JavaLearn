package com.demo.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataIntegrityViolationException;


@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(DuplicateKeyException.class)//关于添加学生重复插入
    public Result doDuplicateKeyException() {
        return new Result(Code.BUSINESS_ERR, null, "不能重复插入");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)//关于空值插入
    public Result doDataIntegrityViolationException(DataIntegrityViolationException e) {
        e.printStackTrace();
        return new Result(Code.BUSINESS_ERR, null, "表单不能有空值");
    }

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex) {
        System.out.println("出现了异常:\n" + ex);
        // 其他异常，没有预计的异常，发送固定消息，安抚客户
        return new Result(Code.SYSTEM_UNKNOWN_ERR, null, "系统繁忙，请稍后再试！");
    }
}
