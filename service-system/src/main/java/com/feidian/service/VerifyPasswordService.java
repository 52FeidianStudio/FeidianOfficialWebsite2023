package com.feidian.service;

import com.feidian.dto.VerifyPasswordDTO;
import com.feidian.responseResult.ResponseResult;

public interface VerifyPasswordService {
    ResponseResult verifyProcess(VerifyPasswordDTO verifyPasswordDTO);
}
