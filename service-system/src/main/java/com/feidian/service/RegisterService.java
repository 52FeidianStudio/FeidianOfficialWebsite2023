package com.feidian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feidian.dto.RegisterDTO;
import com.feidian.po.Register;
import feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService extends IService<Register> {
    ResponseResult submitRegister(RegisterDTO registerDTO);

    ResponseResult submitImage(MultipartFile imageFile);

    ResponseResult formalView(Long registerId);
}
