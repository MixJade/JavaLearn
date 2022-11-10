package controller;

import exception.BusinessException;
import exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice用于标识当前类为REST风格对应的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex) {
        // 系统异常，可预计而无法避免的异常，发送固定消息，安抚客户
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex) {
        // 业务异常，发送消息给客户，提醒规范操作
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex) {
        // 其他异常，没有预计的异常，发送固定消息，安抚客户
        return new Result(Code.SYSTEM_UNKNOWN_ERR, null, "系统繁忙，请稍后再试！");
    }
}
