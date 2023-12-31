package com.feidian.service.impl;
import com.feidian.bo.GraduatesBO;
import com.feidian.constants.SystemConstants;
import com.feidian.dto.AddGraduateDTO;
import com.feidian.dto.EditGraduateDTO;
import com.feidian.dto.PageDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.mapper.GraduatesMapper;

import com.feidian.po.Graduates;
import com.feidian.service.GraduatesService;
import com.feidian.responseResult.ResponseResult;

import com.feidian.util.BeanCopyUtils;
import com.feidian.vo.GraduatesVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private GraduatesMapper graduatesMapper;

    //分页查询毕业生信息
    @Override
    public ResponseResult getMessage(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(),pageDTO.getPageSize());
        List<GraduatesBO> graduates = graduatesMapper.getGraduatesMessage();
        ArrayList<GraduatesVO> graduatesVOS = new ArrayList<>();
        for(GraduatesBO graduate:graduates){
            GraduatesVO vo = BeanCopyUtils.copyProperty(graduate, GraduatesVO.class);
            vo.setFaculty(graduate.getFaculty().getFacultyName());
            vo.setDepartment(graduate.getDepartment().getDepartmentName());
            vo.setSubject(graduate.getSubject().getSubjectName());
            graduatesVOS.add(vo);
        }
        PageInfo<GraduatesVO> pageInfo = new PageInfo<>(graduatesVOS);
        return ResponseResult.successResult(pageInfo);
    }

    @Override
    public ResponseResult addGraduateInformation(AddGraduateDTO addGraduateDTO) {
        Graduates graduate = addGraduate(addGraduateDTO);

        // 存入数据库
        graduatesMapper.insertGraduate(graduate);

        return ResponseResult.successResult();

    }

    @Override
    public ResponseResult editGraduateInformation(EditGraduateDTO editGraduateDTO) {
        Graduates updateGraduate = updateGraduate(editGraduateDTO);

        // 执行数据库更新操作
        graduatesMapper.updateGraduate(updateGraduate);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult deleteGraduateInformation(Long id) {
        // 检查数据库中是否存在提供的 graduateId
        Graduates graduates = graduatesMapper.getGraduateById(id);

        // 逻辑删除
        graduates.setIsDeleted(SystemConstants.DELETE);

        return ResponseResult.successResult();
    }

    private Graduates addGraduate(AddGraduateDTO addGraduateDTO){
        // 创建并设置数据
        Graduates graduate = new Graduates();
        graduate.setName(addGraduateDTO.getName());
        graduate.setFacultyId(addGraduateDTO.getFacultyId());
        graduate.setSubjectId(addGraduateDTO.getSubjectId());
        graduate.setCity(addGraduateDTO.getCity());
        graduate.setCompany(addGraduateDTO.getCompany());
        graduate.setIsDeleted(SystemConstants.NOT_DELETED);
        graduate.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        graduate.setCreateBy(addGraduateDTO.getCreateBy());

        return graduate;
    }

    private Graduates updateGraduate(EditGraduateDTO editGraduateDTO){
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

        return existingGraduate;
    }
}
