package com.feidian.controller;

import com.feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgetPasswordController {
    //TODO 忘记密码接口
    @Autowired
    private ForgetPasswordService forgetPasswordService;
    @PostMapping("/forgetPassword")
    public ResponseResult forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO ){

    }
}
