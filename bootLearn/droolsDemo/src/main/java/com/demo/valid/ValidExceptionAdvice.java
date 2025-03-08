package com.demo.valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 处理参数校验的异常切片
 */
@RestControllerAdvice
public class ValidExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(ValidExceptionAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class) // Valid注解数值校验异常
    public ResponseEntity<String> doValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if (fieldError != null) {
            String message = String.format("%s[%s]:%s"
                    , fieldError.getObjectName()
                    , fieldError.getField()
                    , fieldError.getDefaultMessage());
            log.error(message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("属性校验出错", HttpStatus.BAD_REQUEST);
        }
    }

}
