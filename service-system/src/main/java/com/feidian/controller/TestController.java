package com.feidian.controller;


import feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import com.feidian.service.RegisterService;

@Controller
public class TestController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/test/submitRegister")
    public ResponseResult submitRegister(){
        return registerService.submitRegister();
    }

}
