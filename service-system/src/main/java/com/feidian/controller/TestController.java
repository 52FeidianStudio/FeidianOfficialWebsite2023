package com.feidian.controller;


import com.feidian.dto.RegisterDTO;
import com.feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import com.feidian.service.RegisterService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TestController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/test/submitRegister")
    public ResponseResult submitRegister(@RequestBody RegisterDTO registerDTO){
        return registerService.submitRegister(registerDTO);
    }

}
