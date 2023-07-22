package com.feidian.handle.exception;


import com.feidian.enums.HttpCodeEnum;
import com.feidian.responseResult.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    //未自定义的异常统一处理
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印日志
        log.error("出现异常",e);
        //封装统一返回
        return ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }


}

