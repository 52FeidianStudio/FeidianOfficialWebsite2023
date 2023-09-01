package com.feidian.controller;

import com.feidian.dto.RegisterOperDTO;
import com.feidian.dto.RegisterFormDTO;
import com.feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.feidian.service.RegisterService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    //上传大头照
    @PreAuthorize("hasAuthority('SUBMIT_REGISTER_FORM')")
    @PostMapping("/submitImage")
    public ResponseResult submitImage(@RequestPart("imageFile") MultipartFile imageFile) {
        return registerService.submitImage(imageFile);
    }

    //上传报名表
    @PreAuthorize("hasAuthority('SUBMIT_REGISTER_FORM')")
    @PostMapping("/submitRegister")
    public ResponseResult submitRegister(@RequestBody RegisterFormDTO registerFormDTO) {
        return registerService.submitRegister(registerFormDTO);
    }

    @PreAuthorize("hasAuthority('EDIT_REGISTER_CONTENT')")
    @GetMapping("/viewRegisterInfo")
    public ResponseResult viewRegisterInfo() {
        return registerService.viewRegisterInfo();
    }

    //      修改报名表
    @PreAuthorize("hasAuthority('EDIT_REGISTER_CONTENT')")
    @PostMapping("/editRegister")
    public ResponseResult editRegister(@RequestBody RegisterFormDTO registerFormDTO) {
        return registerService.editRegister(registerFormDTO);
    }


    // 正式查看（未审核的报名表会被修改状态为已查看）
    @PreAuthorize("hasAuthority('EXAMINE_REGISTER')")
    @PostMapping("/examineRegister")
    public ResponseResult examineRegister(@RequestBody RegisterOperDTO registerOperDTO) {
        return registerService.examineRegister(registerOperDTO.getRegisterId());
    }


    //      设置报名表状态（是否通过）
    @PreAuthorize("hasAuthority('EXAMINE_REGISTER')")
    @PostMapping("/isApproved")
    public ResponseResult isApproved(@RequestBody RegisterOperDTO registerOperDTO) {
        return registerService.isApproved(registerOperDTO.getRegisterId(), registerOperDTO.getIsApprovedFlag(), registerOperDTO.getEmailContent());
    }


    // 普通查看
    @PreAuthorize("hasAuthority('VIEW_REGISTER_BY_FILTER')")
    @PostMapping("/viewRegister")
    public ResponseResult viewRegister(@RequestBody RegisterOperDTO registerOperDTO) {
        return registerService.viewRegister(registerOperDTO.getRegisterId());
    }

    //      获取年级、专业、申请组别、报名表状态
    @PreAuthorize("hasAuthority('VIEW_REGISTER_BY_FILTER')")
    @PostMapping("/selectQueryCategory")
    public ResponseResult selectQueryCategory(@RequestBody RegisterOperDTO registerOperDTO) {
        return registerService.selectQueryCategory(registerOperDTO.getQueryCategoryId());
    }

    //      按年级、专业、申请组别、报名表状态筛选
    @PreAuthorize("hasAuthority('VIEW_REGISTER_BY_FILTER')")
    @PostMapping("/selectByQueryRegister")
    public ResponseResult selectByQueryRegister(@RequestBody RegisterOperDTO registerOperDTO) {
        return registerService.selectByQueryRegister(registerOperDTO);
    }

}
