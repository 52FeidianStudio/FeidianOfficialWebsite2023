package com.feidian.controller;

import com.feidian.dto.AddRoleDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // 查看所有用户的等级
    @PostMapping("/view")
    @PreAuthorize("hasAuthority('test')")
    public ResponseResult viewAllRoleInformation(){
        return roleService.viewAllRoleInformation();
    }

//    // 修改用户的等级
//    @PostMapping("/modify")
//    @PreAuthorize("hasAuthority('test')")
//    public ResponseResult modifyRole(@RequestBody AddGraduateDTO addGraduateDTO){
//        return roleService.modifyRole(addGraduateDTO);
//    }

    // 直接创建新用户并赋予等级
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('test')")
    public ResponseResult addRole(@RequestBody AddRoleDTO dto){
        return roleService.addRole(dto);
    }

}
