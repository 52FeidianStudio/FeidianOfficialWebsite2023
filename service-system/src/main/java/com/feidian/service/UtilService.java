package com.feidian.service;

import feidian.responseResult.ResponseResult;

public interface UtilService {
    ResponseResult sendVerifyCode(String emailAddress, String label);
}
