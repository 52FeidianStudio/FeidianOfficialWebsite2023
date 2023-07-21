package com.feidian.service.impl;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.dto.RegisterDTO;
import com.feidian.mapper.RegisterMapper;
import com.feidian.po.Register;
import feidian.responseResult.ResponseResult;
import org.springframework.stereotype.Service;
import com.feidian.service.RegisterService;


@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper,Register> implements RegisterService {

    private static final int DESIRE_DEPARTMENT_ID_MAX_LENGTH = 11;
    private static final int RESUME_MAX_LENGTH = 512;
    private static final int DIRECTION_MAX_LENGTH = 512;
    private static final int ARRANGEMENT_MAX_LENGTH = 512;
    private static final int REASON_MAX_LENGTH = 1024;
    private static final int IMG_URL_MAX_LENGTH = 255;

    @Override
    public ResponseResult submitRegister(RegisterDTO registerDTO) {
        boolean isComplete = isRegisterDTOComplete(registerDTO);
        if (isComplete == false) {
            return ResponseResult.errorResult(500, "有必填信息未填");
        }

        ResponseResult validateResponseResult = validate(registerDTO);
        if (validateResponseResult.getCode() == 400){
            return validateResponseResult;
        }

        return ResponseResult.successResult(200, "提交报名表成功！");
    }


    private boolean isRegisterDTOComplete(RegisterDTO registerDTO) {
        return StringUtils.isNotBlank(registerDTO.getResume())
                && registerDTO.getDesireDepartmentId() != null
                && StringUtils.isNotBlank(registerDTO.getDirection())
                && StringUtils.isNotBlank(registerDTO.getArrangement())
                && StringUtils.isNotBlank(registerDTO.getReason())
                && StringUtils.isNotBlank(registerDTO.getImgUrl());
    }

    private ResponseResult validate(RegisterDTO registerDTO) {
        if (registerDTO == null) {
            return ResponseResult.errorResult(400, "注册信息不能为空");
        }

        if (registerDTO.getDesireDepartmentId() != null && String.valueOf(registerDTO.getDesireDepartmentId()).length() > DESIRE_DEPARTMENT_ID_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "想要申请的组别超出长度限制");
        }

        if (registerDTO.getResume() != null && registerDTO.getResume().length() > RESUME_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "个人简介超出长度限制");
        }

        if (registerDTO.getDirection() != null && registerDTO.getDirection().length() > DIRECTION_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "发展方向超出长度限制");
        }

        if (registerDTO.getArrangement() != null && registerDTO.getArrangement().length() > ARRANGEMENT_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "大学四年的整体规划超出长度限制");
        }


        if (registerDTO.getReason() != null && registerDTO.getReason().length() > REASON_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "对你所选方向的了解以及为什么想选择此方向超出长度限制");
        }

        if (registerDTO.getImgUrl() != null && registerDTO.getImgUrl().length() > IMG_URL_MAX_LENGTH) {
            return ResponseResult.errorResult(400, "头像URL超出长度限制");
        }

        return ResponseResult.successResult(200, "注册信息验证通过");
    }

}
