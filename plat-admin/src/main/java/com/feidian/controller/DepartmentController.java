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

    /**
     * 编辑部门介绍信息
     *
     * @param dto 包含待编辑部门介绍信息的DTO
     * @return ResponseResult 表示操作结果的响应对象
     */
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('EDIT_DEPARTMENT')")
    public ResponseResult editDepartmentIntroduction(@RequestBody EditDepartmentIntroductionDTO dto) {
        return departmentService.editDepartmentIntroduction(dto);
    }
}


