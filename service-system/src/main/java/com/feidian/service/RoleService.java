package com.feidian.service;


import com.feidian.dto.AddGraduateDTO;
import com.feidian.dto.AddRoleDTO;
import com.feidian.responseResult.ResponseResult;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 11:27:04
 */
public interface RoleService  {

    ResponseResult addRole(AddRoleDTO dto);

    ResponseResult viewAllRoleInformation();
}
