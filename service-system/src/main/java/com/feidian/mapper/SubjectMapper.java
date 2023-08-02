package com.feidian.mapper;


import com.feidian.dto.SubjectDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Subject)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 11:28:12
 */
@Mapper
@Repository
public interface SubjectMapper{
    List<Long> selectIdAndFacultyIdBySubjectName(String subjectName);

}

