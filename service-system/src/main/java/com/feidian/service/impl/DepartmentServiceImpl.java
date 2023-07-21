package com.feidian.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.mapper.DepartmentMapper;
import com.feidian.po.Department;
import com.feidian.service.DepartmentService;
import org.springframework.stereotype.Service;

/**
 * (Department)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:14:23
 */
@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}
