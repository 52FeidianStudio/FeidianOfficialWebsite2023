package com.feidian.service.impl;

import com.feidian.dto.EditDepartmentIntroductionDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.mapper.DepartmentMapper;
import com.feidian.po.Department;
import com.feidian.po.Graduates;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.DepartmentService;
import com.feidian.util.BeanCopyUtils;
import com.feidian.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Department)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:14:23
 */
@Service("departmentService")
public class DepartmentServiceImpl  implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public ResponseResult editDepartmentIntroduction(EditDepartmentIntroductionDTO dto) {

        Long departmentId = dto.getDepartmentId();

        // 步骤 1:检查数据库中是否存在提供的 departmentId
        Department existingDepartment = departmentMapper.getDepartmentById(departmentId);

        if (existingDepartment.getIsDeleted() == null &&existingDepartment.getIsDeleted()!=0) {
            return ResponseResult.errorResult(HttpCodeEnum.DEPARTMENT_IS_DELETE);
        }

        // 步骤 2:使用 DTO 中非空的字段更新现有系信息
        String introduction = dto.getIntroduction();
        if (introduction != null) {
            existingDepartment.setIntroduction(introduction);
        }

        // 步骤 3:将 updateTime 字段更新为当前时间戳
        existingDepartment.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));

        // 步骤 4:执行数据库更新操作
        departmentMapper.updateDepartment(existingDepartment);

        return ResponseResult.successResult();

    }

    @Override
    public ResponseResult getAllName() {
        List<String> allName = departmentMapper.getAllName();
        return ResponseResult.successResult(allName);
    }

    @Override
    public ResponseResult getByName(String name) {
        String byName = departmentMapper.getByName(name);
        return ResponseResult.successResult(byName);
    }

    @Override
    public ResponseResult getAllMessage() {
        List<Department> allMessage = departmentMapper.getAllMessage();
        List<DepartmentVO> departmentVOS = BeanCopyUtils.copyProperties(allMessage, DepartmentVO.class);
        return ResponseResult.successResult(departmentVOS);
    }
}
