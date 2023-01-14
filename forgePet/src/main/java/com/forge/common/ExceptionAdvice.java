package com.forge.common;

import com.forge.ForgePetApplication;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataIntegrityViolationException;


@RestControllerAdvice
public class ExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ForgePetApplication.class);

    @ExceptionHandler(DuplicateKeyException.class)//关于唯一约束重复插入
    public Result doDuplicateKeyException() {
        return Result.error("不能重复插入");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)//关于空值插入或插入过长
    public Result doDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn("数据完整性违规异常，可能空值插入或插入过长==>" + e);
        return Result.error("数据完整性违规异常");
    }

    @ExceptionHandler(UnauthorizedException.class)//操作没有角色权限
    public Result doUnauthorizedException() {
        return Result.error("没有操作权限");
    }

    @ExceptionHandler(Exception.class)// 其他异常，没有预计的异常，发送固定消息，安抚客户
    public Result doOtherException(Exception ex) {
        log.warn("出现了异常" + ex);
        return Result.error("系统繁忙，请稍后再试！");
    }
}
