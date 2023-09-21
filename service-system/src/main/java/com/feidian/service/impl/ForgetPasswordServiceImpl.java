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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class ForgetPasswordServiceImpl implements ForgetPasswordService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {

        String address = forgetPasswordDTO.getEmail();


        if(StringUtils.isBlank(address)) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL);
        }
        String code = VerifyCode.setVerifyCode();
        redisCache.setCacheObject("forget:" + address,code);
        EmailUtil.sendEmail(address,code);
        System.out.println(passwordEncoder.encode("@Soososoo123"));
        return ResponseResult.successResult();
    }



}



