package com.feidian.service.impl;

import com.feidian.dto.ForgetPasswordDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.ForgetPasswordMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.ForgetPasswordService;
import com.feidian.util.RedisCache;
import com.feidian.util.serviceUtil.EmailUtil;
import com.feidian.util.serviceUtil.VerifyCode;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ForgetPasswordMapper forgetPasswordMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {

        String address = forgetPasswordDTO.getAddress();
        String username = forgetPasswordDTO.getUsername();
        //验证用户名和邮箱是否匹配
        if(StringUtils.isBlank(username)){
            throw new SystemException(HttpCodeEnum.USERNAME_NOT_NULL);
        }else if(StringUtils.isBlank(address)){
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL);
        }
        String email = forgetPasswordMapper.getEmailByUsername(username);
        //TODO 判断用户名邮箱是否匹配，发送验证码，验证验证码是否正确
        if(!email.equals(address)){
            throw new SystemException(HttpCodeEnum.USERNAME_EMAIL_NOT_MATCH);
        }
        String code = VerifyCode.setVerifyCode();

        redisCache.setCacheObject("forget:" + address,code);
        EmailUtil.sendEmail(address,code);
        return ResponseResult.successResult();
    }



}



