package com.fedian.handler;

import com.alibaba.fastjson.JSON;
import feidian.enums.HttpCodeEnum;
import feidian.responseResult.ResponseResult;
import feidian.util.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandleImpl implements AccessDeniedHandler {

    //权限的统一异常处理
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(HttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
