package com.feidian.controller;

import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @GetMapping("/faculty/getAllName")
    public ResponseResult getAllName(){
        return facultyService.getAllName();
    }
    @GetMapping("/subject/getSubjectNameByFaculty")
    public ResponseResult getSubjectNameByFaculty(String name){
        return facultyService.getSubjectNameByFaculty(name);
    }
}
