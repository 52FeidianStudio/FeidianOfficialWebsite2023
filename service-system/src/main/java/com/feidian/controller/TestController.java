package com.feidian.controller;

import com.feidian.dto.RegisterDTO;
import com.feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.feidian.service.RegisterService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RegisterService registerService;

    //上传大头照
    @PostMapping("/submitImage")
    public ResponseResult submitImage(@RequestPart("imageFile") MultipartFile imageFile){
        return registerService.submitImage(imageFile);
    }

    //上传报名表
    @PostMapping("/submitRegister")
    public ResponseResult submitRegister(@RequestBody RegisterDTO registerDTO){
        return registerService.submitRegister(registerDTO);
    }

    //      设置报名表状态（是否通过）
    @PostMapping("/isApproved")
    public ResponseResult isApproved(@RequestBody Long registerId, @RequestBody String isApprovedFlag){
        return registerService.isApproved(registerId, isApprovedFlag);
    }

    //      修改报名表
    @PostMapping("/editRegister")
    public ResponseResult editRegister(@RequestBody RegisterDTO registerDTO){
        return registerService.editRegister(registerDTO);
    }
    // TODO 日志
    // 正式查看
    @PostMapping("/formalView")
    public ResponseResult formalView(@RequestBody Long registerId){
        return registerService.formalView(registerId);
    }

    //      按年级筛选
    @PostMapping("/selectByGradeName")
    public ResponseResult selectByGradeName(@RequestBody String gradeName){
        return registerService.selectByGradeName(gradeName);
    }

    //      按专业筛选
    @PostMapping("/selectBySubjectId")
    public ResponseResult selectBySubjectId(@RequestBody Long subjectId){
        return registerService.selectBySubjectId(subjectId);
    }

    //      按申请组别筛选
    @PostMapping("/selectByDesireDepartmentId")
    public ResponseResult selectByDesireDepartmentId(@RequestBody Long desireDepartmentId){
        return registerService.selectByDesireDepartmentId(desireDepartmentId);
    }

    //      按报名表状态筛选
    @PostMapping("/selectByStatus")
    public ResponseResult selectByStatus(@RequestBody String status){
        return registerService.selectByStatus(status);
    }

}
