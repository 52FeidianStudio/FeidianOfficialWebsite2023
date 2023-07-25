package com.feidian.controller;

import com.feidian.dto.ForgetPasswordDTO;
import com.feidian.dto.VerifyPasswordDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.ForgetPasswordService;
import com.feidian.service.VerifyPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgetPasswordController {
    //TODO 忘记密码接口
    @Autowired
    private ForgetPasswordService forgetPasswordService;
    @Autowired
    private VerifyPasswordService verifyPasswordService;
    //发送验证码
    @PostMapping("/forgetPassword")
    public ResponseResult forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO){
        return forgetPasswordService.forgetPassword(forgetPasswordDTO);
    }
    //验证，修改密码
    @PostMapping("/verifyProcess")
    public ResponseResult verifyProcess(@RequestBody VerifyPasswordDTO verifyPasswordDTO){
        return verifyPasswordService.verifyProcess(verifyPasswordDTO);
    }

}
