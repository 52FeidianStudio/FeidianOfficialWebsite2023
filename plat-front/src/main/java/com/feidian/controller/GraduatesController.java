package com.feidian.controller;

import com.feidian.dto.AddGraduateDTO;
import com.feidian.dto.EditGraduateDTO;
import com.feidian.dto.PageDTO;
import com.feidian.service.GraduatesService;
import com.feidian.responseResult.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graduate")
public class GraduatesController {
    @Autowired
    private GraduatesService graduatesService;
    @GetMapping("/getMessage")
    public ResponseResult getMessage(){
        return graduatesService.getMessage();
    }


}
