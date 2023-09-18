package com.feidian.service;

import com.feidian.dto.FrontUserDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.vo.FrontUserRegisterVO;
import com.feidian.vo.FrontUserVO;

public interface FrontUserService {
    ResponseResult update(FrontUserVO frontUserVO);
    ResponseResult update(FrontUserRegisterVO frontUserRegisterVO);

    ResponseResult getMessage();
}
