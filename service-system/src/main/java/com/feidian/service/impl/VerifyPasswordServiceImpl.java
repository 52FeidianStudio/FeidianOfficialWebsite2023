package com.feidian.service.impl;
import com.feidian.dto.VerifyPasswordDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.VerifyPasswordMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.VerifyPasswordService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VerifyPasswordServiceImpl implements VerifyPasswordService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private VerifyPasswordMapper verifyPasswordMapper;
    @Override
    public ResponseResult verifyProcess(VerifyPasswordDTO verifyPasswordDTO) {
        if(StringUtils.isBlank(verifyPasswordDTO.getCode())){
            throw new SystemException(HttpCodeEnum.CODE_NULL);
        }
        if(verifyPasswordDTO.getCode().equals(redisTemplate.opsForValue().get(verifyPasswordDTO.getAddress()))){
           verifyPasswordMapper.updatePassword(verifyPasswordDTO.getPassword(),verifyPasswordDTO.getAddress());
           return ResponseResult.successResult();
        }else{
            throw new SystemException(HttpCodeEnum.CODE_ERR);
        }
    }
}
