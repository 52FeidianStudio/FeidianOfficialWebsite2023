package com.feidian.controller;

import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FrontUserService;
import com.feidian.vo.FrontUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class FrontUserController {
    @Autowired
    private FrontUserService frontUserService;
    //修改个人信息
    @PutMapping("/update")
    public ResponseResult update(@RequestBody FrontUserVO frontUserVO){
        return frontUserService.update(frontUserVO);
    }
    //查看个人信息
    @GetMapping("/getMessage")
    public ResponseResult getMessage(){
        return frontUserService.getMessage();
    }



}
