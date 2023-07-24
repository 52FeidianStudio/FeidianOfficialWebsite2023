package com.feidian.controller;

import com.feidian.dto.AddGraduateDTO;
import com.feidian.dto.EditGraduateDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.GraduatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graduates")
public class GraduatesController {
    @Autowired
    private GraduatesService graduatesService;

    // 添加毕业生去向信息
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADD_GRADUATE')")
    public ResponseResult addGraduateInformation(@RequestBody AddGraduateDTO addGraduateDTO){
        return graduatesService.addGraduateInformation(addGraduateDTO);
    }

    // 编辑毕业生去向信息
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('EDIT_GRADUATE')")
    public ResponseResult editGraduateInformation(@RequestBody EditGraduateDTO editGraduateDTO){
        return graduatesService.editGraduateInformation(editGraduateDTO);
    }

    // 删除毕业生去向信息
    @PostMapping("/delete/{graduateId}")
    @PreAuthorize("hasAuthority('DELETE_GRADUATE')")
    public ResponseResult deleteGraduateInformation(@PathVariable("graduateId") Long id){
        return graduatesService.deleteGraduateInformation(id);
    }

}