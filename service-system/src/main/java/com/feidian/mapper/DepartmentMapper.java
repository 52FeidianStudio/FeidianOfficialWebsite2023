package com.feidian.mapper;


import com.feidian.po.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (Department)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 11:16:49
 */
@Mapper
@Repository
public interface DepartmentMapper  {

    Department getDepartmentById(Long id);

    void updateDepartment(Department department);
}

