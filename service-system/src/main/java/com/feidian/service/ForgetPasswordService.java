package com.feidian.service;

import com.feidian.dto.ForgetPasswordDTO;
import com.feidian.responseResult.ResponseResult;

public interface ForgetPasswordService {
    ResponseResult forgetPassword(ForgetPasswordDTO forgetPasswordDTO);
}
