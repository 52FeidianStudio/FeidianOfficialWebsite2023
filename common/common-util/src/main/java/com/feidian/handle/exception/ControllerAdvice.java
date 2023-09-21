package com.feidian.handle.exception;


import com.alibaba.fastjson.JSONObject;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.responseResult.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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




    /**
     * 参数合法性校验异常
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationBodyException(MethodArgumentNotValidException exception){

        StringBuffer buffer = new StringBuffer();

        BindingResult result  = exception.getBindingResult();
        if (result.hasErrors()) {

            List<ObjectError> errors = result.getAllErrors();

            errors.forEach(p ->{

                FieldError fieldError = (FieldError) p;
                log.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
                        "},errorMessage{"+fieldError.getDefaultMessage()+"}");
                buffer.append(fieldError.getDefaultMessage()).append(",");
            });
        }
        ResponseResult response = ResponseResult.errorResult(HttpCodeEnum.INVALID_PARAM);
        response.setMsg(buffer.toString().substring(0, buffer.toString().length()-1));
        return JSONObject.toJSONString(response);
    }
}

