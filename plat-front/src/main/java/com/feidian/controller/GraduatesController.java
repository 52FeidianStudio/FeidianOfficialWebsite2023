package com.feidian.controller;

import com.feidian.dto.PageDTO;
import com.feidian.service.GraduatesService;
import feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graduate")
public class GraduatesController {
    @Autowired
    private GraduatesService graduatesService;
    @GetMapping("/getMessage")
    public ResponseResult getMessage(PageDTO pageDTO){
        return graduatesService.getMessage(pageDTO);
    }
}
