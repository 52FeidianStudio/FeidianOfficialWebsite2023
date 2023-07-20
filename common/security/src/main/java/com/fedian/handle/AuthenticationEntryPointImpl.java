package com.fedian.handle;

import com.alibaba.fastjson.JSON;
import feidian.enums.HttpCodeEnum;
import feidian.responseResult.ResponseResult;
import feidian.util.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    //登录的统一异常处理
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        //此时出现一个问题，无token登录的报错信息和用户名密码错误的报错信息相同
        //但是报错的异常信息不同
        //用户名或密码错误:  BadCredentialsException
        //无token登录:     InsufficientAuthenticationException
        ResponseResult result = null;
        //通过报错信息对上述两种情况加以区分
        if(e instanceof BadCredentialsException){
            result = ResponseResult.errorResult(HttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if(e instanceof InsufficientAuthenticationException){
            result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN.getCode(),e.getMessage());
        }else {
            result = ResponseResult.errorResult(HttpCodeEnum.SYSTEM_ERROR.getCode(),"暂未处理的登陆状态异常");
        }


        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
