package com.feidian.service.impl;

import com.feidian.dto.FrontUserDTO;
import com.feidian.dto.FrontUserRegisterDTO;
import com.feidian.mapper.FrontUserMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FrontUserService;
import com.feidian.util.BeanCopyUtils;
import com.feidian.util.SecurityUtils;
import com.feidian.vo.FrontUserRegisterVO;
import com.feidian.vo.FrontUserVO;
import com.feidian.vo.MergeFrontUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrontUserServiceImpl implements FrontUserService {
    @Autowired
    private FrontUserMapper frontUserMapper;

    @Override
    public ResponseResult update(FrontUserVO frontUserVO) {

        //将不带id的形式转化成带id的形式
        FrontUserDTO frontUserDTO = BeanCopyUtils.copyProperty(frontUserVO, FrontUserDTO.class);
        Long userId = SecurityUtils.getUserId();
        frontUserDTO.setId(userId);
        frontUserDTO.setDepartmentId(frontUserMapper.getDIdByName(frontUserVO.getDepartmentName()));
        frontUserDTO.setFacultyId(frontUserMapper.getFIdByName(frontUserVO.getFacultyName()));
        frontUserDTO.setSubjectId(frontUserMapper.getSIdByName(frontUserVO.getFacultyName()));
        frontUserMapper.updateUser(frontUserDTO);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult update(FrontUserRegisterVO frontUserRegisterVO) {
        //将不带id的形式转化成带id的形式
        FrontUserRegisterDTO frontUserRegisterDTO = BeanCopyUtils.copyProperty(frontUserRegisterVO, FrontUserRegisterDTO.class);
        Long userId = SecurityUtils.getUserId();
        frontUserRegisterDTO.setId(userId);
        frontUserRegisterDTO.setFacultyId(frontUserMapper.getFIdByName(frontUserRegisterVO.getFacultyName()));
        frontUserMapper.updateUserRegister(frontUserRegisterDTO);
        return ResponseResult.successResult();
    }



    @Override
    public ResponseResult getMessage() {
        Long userId = SecurityUtils.getUserId();
        FrontUserVO frontUserVO = frontUserMapper.getMessageById(userId);
        //关联register表拿到status
        String status = frontUserMapper.getStatusById(userId);
        MergeFrontUserVO mergeFrontUserVO = new MergeFrontUserVO(frontUserVO, status);
        return ResponseResult.successResult(mergeFrontUserVO);
    }
}
