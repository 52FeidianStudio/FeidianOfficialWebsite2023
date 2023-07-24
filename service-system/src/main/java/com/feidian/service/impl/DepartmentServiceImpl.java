package com.feidian.service.impl;

import com.feidian.mapper.DepartmentMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.DepartmentService;
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
    public ResponseResult getAllName() {
        List<String> allName = departmentMapper.getAllName();
        return ResponseResult.successResult(allName);
    }

    @Override
    public ResponseResult getByName(String name) {
        String byName = departmentMapper.getByName(name);
        return ResponseResult.successResult(byName);
    }
}
