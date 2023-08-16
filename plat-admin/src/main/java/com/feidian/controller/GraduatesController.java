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

    /**
     * 添加毕业生去向信息
     *
     * @param addGraduateDTO 包含毕业生信息的DTO
     * @return ResponseResult indicating the result of the operation
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADD_GRADUATE')")
    public ResponseResult addGraduateInformation(@RequestBody AddGraduateDTO addGraduateDTO) {
        return graduatesService.addGraduateInformation(addGraduateDTO);
    }

    /**
     * 编辑毕业生去向信息
     *
     * @param editGraduateDTO 包含待编辑毕业生信息的DTO
     * @return ResponseResult indicating the result of the operation
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('EDIT_GRADUATE')")
    public ResponseResult editGraduateInformation(@RequestBody EditGraduateDTO editGraduateDTO) {
        return graduatesService.editGraduateInformation(editGraduateDTO);
    }

    /**
     * 删除毕业生去向信息
     *
     * @param id 毕业生ID
     * @return ResponseResult indicating the result of the operation
     */
    @PostMapping("/delete/{graduateId}")
    @PreAuthorize("hasAuthority('DELETE_GRADUATE')")
    public ResponseResult deleteGraduateInformation(@PathVariable("graduateId") Long id) {
        return graduatesService.deleteGraduateInformation(id);
    }
}
