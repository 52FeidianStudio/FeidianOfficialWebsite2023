package com.feidian.service;

import com.feidian.responseResult.ResponseResult;

public interface UtilService {
    ResponseResult sendVerifyCode(String emailAddress, String label);
}
