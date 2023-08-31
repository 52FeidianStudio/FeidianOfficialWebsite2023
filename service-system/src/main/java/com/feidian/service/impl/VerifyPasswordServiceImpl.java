package com.feidian.service.impl;
import com.feidian.dto.VerifyPasswordDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.VerifyPasswordMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.VerifyPasswordService;
import com.feidian.util.RedisCache;
import com.feidian.util.serviceUtil.Base64Util;
import com.feidian.util.serviceUtil.StandardPasswordUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VerifyPasswordServiceImpl implements VerifyPasswordService {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private VerifyPasswordMapper verifyPasswordMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult verifyProcess(VerifyPasswordDTO verifyPasswordDTO) {
        // 对密码是否符合规范的判断
        if(!StandardPasswordUtil.isPasswordStandardized(verifyPasswordDTO.getPassword())){
            throw new SystemException(HttpCodeEnum.PASSWORD_NOT_STANDARDIZED);
        }
        if(StringUtils.isBlank(verifyPasswordDTO.getCode())){
            throw new SystemException(HttpCodeEnum.CODE_NULL);
        }
        if(verifyPasswordDTO.getCode().equals(redisCache.getCacheObject("forget:" + verifyPasswordDTO.getEmail()))){
            //对密码加密
            verifyPasswordDTO.setPassword(passwordEncoder.encode(verifyPasswordDTO.getPassword()));
           verifyPasswordMapper.updatePassword(verifyPasswordDTO.getPassword(),verifyPasswordDTO.getEmail());
           return ResponseResult.successResult();
        }else{
            throw new SystemException(HttpCodeEnum.CODE_ERR);
        }
    }
}
