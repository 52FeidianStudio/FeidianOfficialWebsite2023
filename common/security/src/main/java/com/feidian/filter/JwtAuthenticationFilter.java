package com.feidian.filter;

import com.alibaba.fastjson.JSON;

import com.feidian.bo.LoginUser;
import feidian.enums.HttpCodeEnum;
import feidian.responseResult.ResponseResult;
import feidian.util.JwtUtil;
import feidian.util.RedisCache;
import feidian.util.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        //如果token为空， 则直接放行，认证登录
        if(!StringUtils.hasText(token))      {
            filterChain.doFilter(request,response);
            return;
        }
        //解析获得userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //两种情况抛出异常
            //1.token超时
            //2.前端传来的token非法
            e.printStackTrace();
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }

        String userId = claims.getSubject();

        //从redis中获用户信息
        LoginUser LoginUser = redisCache.getCacheObject("bloglogin:"+userId);

        if(Objects.isNull(LoginUser)){
            //登录过期，需要重新登录
            //响应告诉前端需要重新登录
            ResponseResult result = ResponseResult.errorResult(HttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //将用户信息存入SecurityContextHolder

        //TODO 将loginUser中的权限集合传入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(LoginUser, null, LoginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //过滤器放行
        filterChain.doFilter(request,response);

    }
}
