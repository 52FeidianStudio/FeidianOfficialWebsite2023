package com.feidian.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Faculty)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 11:20:09
 */
@Mapper
@Repository
public interface FacultyMapper  {

    List<String> getAllName();

    List<String> getSubjectNameByFaculty(String name);
}

