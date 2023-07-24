package com.feidian.controller;
import com.feidian.dto.EditDepartmentIntroductionDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('EDIT_DEPARTMENT')")
    public ResponseResult editDepartmentIntroduction(@RequestBody EditDepartmentIntroductionDTO dto){
        return departmentService.editDepartmentIntroduction(dto);
    }
}

