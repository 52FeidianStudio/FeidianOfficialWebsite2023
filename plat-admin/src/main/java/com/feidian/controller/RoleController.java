package com.feidian.controller;

import com.feidian.dto.AddRoleDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查看所有用户的等级
     *
     * @return ResponseResult 表示操作结果的响应对象
     */
    @GetMapping("/view")
    @PreAuthorize("hasAuthority('VIEW_ALL_ROLE')")
    public ResponseResult viewAllRoleInformation(){
        return roleService.viewAllRoleInformation();
    }

//    // 修改用户的等级
//    @PostMapping("/modify")
//    @PreAuthorize("hasAuthority('test')")
//    public ResponseResult modifyRole(@RequestBody AddGraduateDTO addGraduateDTO){
//        return roleService.modifyRole(addGraduateDTO);
//    }

    /**
     * 直接创建新用户并赋予等级
     *
     * @param dto 包含新用户信息和等级的DTO
     * @return ResponseResult 表示操作结果的响应对象
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADD_NEW_USER')")
    public ResponseResult addRole(@RequestBody AddRoleDTO dto){
        return roleService.addRole(dto);
    }

}
