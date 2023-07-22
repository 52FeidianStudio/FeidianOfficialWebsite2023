package com.feidian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feidian.dto.RegisterDTO;
import com.feidian.po.Register;
import com.feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService extends IService<Register> {
    ResponseResult submitRegister(RegisterDTO registerDTO);

    ResponseResult submitImage(MultipartFile imageFile);

    ResponseResult isApproved(Long registerId, String isApprovedFlag);

    ResponseResult editRegister(RegisterDTO registerDTO);

    ResponseResult formalView(Long registerId);

    ResponseResult selectByGradeName(String gradeName);

    ResponseResult selectBySubjectId(Long subjectId);

    ResponseResult selectByDesireDepartmentId(Long desireDepartmentId);

    ResponseResult selectByStatus(String status);


}
