package com.feidian.service.impl;


import com.feidian.constants.SystemConstants;
import com.feidian.dto.AddGraduateDTO;
import com.feidian.dto.EditGraduateDTO;
import com.feidian.dto.PageDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.GraduatesMapper;
import com.feidian.po.Graduates;
import com.feidian.service.DepartmentService;
import com.feidian.service.FacultyService;
import com.feidian.service.GraduatesService;
import com.feidian.service.SubjectService;
import com.feidian.vo.GraduatesVO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.util.BeanCopyUtils;
import com.github.pagehelper.Page;
import io.micrometer.core.instrument.util.StringUtils;
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
public class GraduatesServiceImpl implements GraduatesService {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GraduatesMapper graduatesMapper;

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

    @Override
    public ResponseResult addGraduateInformation(AddGraduateDTO addGraduateDTO) {
        // 先把数据取出
        String name = addGraduateDTO.getName();
        Long facultyId = addGraduateDTO.getFacultyId();
        Long subjectId = addGraduateDTO.getSubjectId();
        Long departmentId = addGraduateDTO.getDepartmentId();
        String city = addGraduateDTO.getCity();
        String company = addGraduateDTO.getCompany();
        Long isDeleted = addGraduateDTO.getIsDeleted();
        java.sql.Timestamp createTime = addGraduateDTO.getCreateTime();
        String createBy = addGraduateDTO.getCreateBy();

        // 对数据进行非空判断
        if (StringUtils.isBlank(name)) {
            throw new SystemException(HttpCodeEnum.NAME_NOT_NULL);
        }
        if (facultyId == null) {
            throw new SystemException(HttpCodeEnum.FACULTYID_NOT_NULL);
        }
        if (subjectId == null) {
            throw new SystemException(HttpCodeEnum.SUBJECT_NOT_NULL);
        }
        if (departmentId == null) {
            throw new SystemException(HttpCodeEnum.DEPARTMENTID_NOT_NULL);
        }
        if (StringUtils.isBlank(city)) {
            throw new SystemException(HttpCodeEnum.CITY_NOT_NULL);
        }
        if (StringUtils.isBlank(company)) {
            throw new SystemException(HttpCodeEnum.COMPANY_NOT_NULL);
        }
        if (isDeleted == null) {
            throw new SystemException(HttpCodeEnum.ISDELETED_NOT_NULL);
        }
        if (createTime == null) {
            throw new SystemException(HttpCodeEnum.CREATETIME_NOT_NULL);
        }
        if (StringUtils.isBlank(createBy)) {
            throw new SystemException(HttpCodeEnum.CREATEBY_NOT_NULL);
        }

        // 创建并设置数据
        Graduates graduate = new Graduates();
        graduate.setName(name);
        graduate.setFacultyId(facultyId);
        graduate.setSubjectId(subjectId);
        graduate.setDepartmentId(departmentId);
        graduate.setCity(city);
        graduate.setCompany(company);
        graduate.setIsDeleted(isDeleted);
        graduate.setCreateTime(createTime);
        graduate.setCreateBy(createBy);

        // 存入数据库
        graduatesMapper.insertGraduate(graduate);

        return ResponseResult.successResult();

    }

    @Override
    public ResponseResult editGraduateInformation(EditGraduateDTO editGraduateDTO) {
        Long graduateId = editGraduateDTO.getGraduateId();

        // 步骤 1：检查数据库中是否存在提供的 graduateId
        Graduates existingGraduate = graduatesMapper.getGraduateById(graduateId);

        if(existingGraduate.getIsDeleted() ==null&&existingGraduate.getIsDeleted()==1){
            ResponseResult.errorResult(HttpCodeEnum.NOT_GRADUATE);
        }


        // 步骤 2：使用 DTO 中非空的字段更新现有毕业生信息
        String name = editGraduateDTO.getName();
        if (name != null) {
            existingGraduate.setName(name);
        }

        Long facultyId = editGraduateDTO.getFacultyId();
        if (facultyId != null) {
            existingGraduate.setFacultyId(facultyId);
        }

        Long subjectId = editGraduateDTO.getSubjectId();
        if (subjectId != null) {
            existingGraduate.setSubjectId(subjectId);
        }

        Long departmentId = editGraduateDTO.getDepartmentId();
        if (departmentId != null) {
            existingGraduate.setDepartmentId(departmentId);
        }

        String city = editGraduateDTO.getCity();
        if (city != null) {
            existingGraduate.setCity(city);
        }

        String company = editGraduateDTO.getCompany();
        if (company != null) {
            existingGraduate.setCompany(company);
        }

        // 步骤 3：将 updateTime 字段更新为当前时间戳
        existingGraduate.setUpdateTime(new java.sql.Timestamp(System.currentTimeMillis()));

        // 步骤 4：执行数据库更新操作
        graduatesMapper.updateGraduate(existingGraduate);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult deleteGraduateInformation(Long id) {
        // 步骤 1：检查数据库中是否存在提供的 graduateId
        Graduates graduates = graduatesMapper.getGraduateById(id);

        // 逻辑删除
        graduates.setIsDeleted(SystemConstants.DELETE);

        return ResponseResult.successResult();
    }


}
