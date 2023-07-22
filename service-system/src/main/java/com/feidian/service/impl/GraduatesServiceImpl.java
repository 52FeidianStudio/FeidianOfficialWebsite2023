package com.feidian.service.impl;


import com.feidian.dto.PageDTO;

import com.feidian.po.Graduates;
import com.feidian.service.DepartmentService;
import com.feidian.service.FacultyService;
import com.feidian.service.GraduatesService;
import com.feidian.service.SubjectService;
import com.feidian.vo.GraduatesVO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.util.BeanCopyUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (Graduates)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
@Service("graduatesService")
public class GraduatesServiceImpl  implements GraduatesService {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SubjectService subjectService;

    //分页查询毕业生信息
    @Override
    public ResponseResult getMessage(PageDTO pageDTO) {
        //创建page对象
        Page<Graduates> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        page(page);
        List<Graduates> records = page.getRecords();
        List<GraduatesVO> graduatesVOS = new ArrayList<>();

        for (Graduates graduates : records) {
            GraduatesVO graduatesVO = BeanCopyUtils.copyProperty(graduates, GraduatesVO.class);
            //将id转化为可读信息
            graduatesVO.setDepartment(departmentService.getById(graduates.getDepartmentId()).getDepartment());
            graduatesVO.setSubject(subjectService.getById(graduates.getSubjectId()).getSubject());
            graduatesVO.setFaculty(facultyService.getById(graduates.getFacultyId()).getFaculty());
            graduatesVOS.add(graduatesVO);
        }
        return ResponseResult.successResult(graduatesVOS);
    }

}
