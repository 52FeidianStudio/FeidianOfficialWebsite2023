package com.feidian.service.impl;

import com.feidian.dto.FrontUserDTO;
import com.feidian.mapper.FrontUserMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FrontUserService;
import com.feidian.util.BeanCopyUtils;
import com.feidian.util.SecurityUtils;
import com.feidian.vo.FrontUserVO;
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
        frontUserDTO.setDepartmentId(frontUserMapper.getDIdByName(frontUserVO.getDepartmentName()));
        frontUserDTO.setFacultyId(frontUserMapper.getFIdByName(frontUserVO.getFacultyName()));
        frontUserDTO.setSubjectId(frontUserMapper.getSIdByName(frontUserVO.getFacultyName()));
        frontUserMapper.updateUser(frontUserDTO);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult getMessage() {
        Long userId = SecurityUtils.getUserId();
        FrontUserVO frontUserVO = frontUserMapper.getMessageById(userId);
        return ResponseResult.successResult(frontUserVO);
    }
}
