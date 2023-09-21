package com.feidian.controller;

import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FrontUserService;
import com.feidian.vo.FrontUserRegisterVO;
import com.feidian.vo.FrontUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class FrontUserController {
    @Autowired
    private FrontUserService frontUserService;
    //修改个人信息
    @Transactional
    @PutMapping("/update")
    public ResponseResult update(@RequestBody FrontUserVO frontUserVO){

        return frontUserService.update(frontUserVO);

    }

    //在报名界面修改用户信息
    @Transactional
    @PreAuthorize("hasAuthority('EDIT_REGISTER_CONTENT')")
    @PostMapping("/updateRegister")
    public ResponseResult update(@Validated @RequestBody FrontUserRegisterVO frontUserRegisterVO){
        return frontUserService.update(frontUserRegisterVO);
    }

    //查看个人信息
    @GetMapping("/getMessage")
    public ResponseResult getMessage(){

        return frontUserService.getMessage();
    }



}
