package com.feidian.service;


import com.feidian.dto.EditDepartmentIntroductionDTO;
import com.feidian.responseResult.ResponseResult;

/**
 * (Department)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 11:14:23
 */
public interface DepartmentService {
    ResponseResult editDepartmentIntroduction(EditDepartmentIntroductionDTO dto);

    ResponseResult getAllName();

    ResponseResult getByName(String name);

    ResponseResult getAllMessage();
}
