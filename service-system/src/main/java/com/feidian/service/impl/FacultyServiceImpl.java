package com.feidian.service.impl;


import com.feidian.mapper.FacultyMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Faculty)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:20:09
 */
@Service("facultyService")
public class FacultyServiceImpl  implements FacultyService {
@Autowired
private FacultyMapper facultyMapper;
    @Override
    public ResponseResult getAllName() {
        List<String> name = facultyMapper.getAllName();
        return ResponseResult.successResult(name);
    }

    @Override
    public ResponseResult getSubjectNameByFaculty(String name) {
        List<String> facultyS = facultyMapper.getSubjectNameByFaculty(name);
        return ResponseResult.successResult(facultyS);
    }
}
