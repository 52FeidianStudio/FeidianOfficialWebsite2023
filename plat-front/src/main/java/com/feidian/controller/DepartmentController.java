package com.feidian.controller;

import com.feidian.responseResult.ResponseResult;
import com.feidian.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired

    private DepartmentService departmentService;
    @GetMapping("/getAllName")
    public ResponseResult getAllName(){
        return departmentService.getAllName();
    }

    @GetMapping("/getByName")
    public ResponseResult getByName(String name){
        return departmentService.getByName(name);
    }

}
