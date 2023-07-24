package com.feidian.service;

import com.feidian.dto.QueryRegisterDTO;
import com.feidian.dto.RegisterDTO;
import com.feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {
    ResponseResult submitRegister(RegisterDTO registerDTO);

    ResponseResult submitImage(MultipartFile imageFile);

    ResponseResult editRegister(RegisterDTO registerDTO);

    ResponseResult examineRegister(Long registerId);

    ResponseResult isApproved(Long registerId, String isApprovedFlag);

    ResponseResult viewRegister(Long registerId);

    ResponseResult selectByQueryRegister(QueryRegisterDTO queryRegisterDTO);
}
