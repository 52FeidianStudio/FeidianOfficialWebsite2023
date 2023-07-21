package com.feidian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feidian.po.Register;
import feidian.responseResult.ResponseResult;

public interface RegisterService extends IService<Register> {
    ResponseResult submitRegister();
}
