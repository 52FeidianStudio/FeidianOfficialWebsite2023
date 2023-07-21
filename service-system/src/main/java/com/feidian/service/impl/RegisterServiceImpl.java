package com.feidian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.mapper.RegisterMapper;
import com.feidian.po.Register;
import feidian.responseResult.ResponseResult;
import org.springframework.stereotype.Service;
import com.feidian.service.RegisterService;

@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, Register> implements RegisterService {
    @Override
    public ResponseResult submitRegister() {
        return null;
    }
}
