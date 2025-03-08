package controller;

import exception.BusinessException;
import exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.DataIntegrityViolationException;

//@RestControllerAdvice用于标识当前类为REST风格对应的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex) {
        // 系统异常，可预计而无法避免的异常，发送固定消息，安抚客户
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex) {
        // 业务异常，发送消息给客户，提醒规范操作
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)//关于添加学生重复插入
    public Result doDuplicateKeyException() {
        return Result.error("不能重复插入");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)//关于空值插入
    public Result doDataIntegrityViolationException() {
        return Result.error("表单不能有空值");
    }

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex) {
        System.out.println("出现了异常:\n" + ex);
        // 其他异常，没有预计的异常，发送固定消息，安抚客户
        return Result.error("系统繁忙，请稍后再试！");
    }
}
