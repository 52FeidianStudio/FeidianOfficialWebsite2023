package com.feidian.service;

import com.feidian.dto.RegisterOperDTO;
import com.feidian.dto.RegisterFormDTO;
import com.feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {
    ResponseResult submitRegister(RegisterFormDTO registerFormDTO);

    ResponseResult submitImage(MultipartFile imageFile);

    ResponseResult editRegister(RegisterFormDTO registerFormDTO);

    ResponseResult examineRegister(Long registerId);

    ResponseResult isApproved(Long registerId, String isApprovedFlag, String emailContent);

    ResponseResult viewRegister(Long registerId);

    ResponseResult viewRegisterInfo();

    ResponseResult selectQueryCategory(Integer queryCategoryId,Integer validatedId);

    ResponseResult selectByQueryRegister(RegisterOperDTO registerOperDTO);


}
