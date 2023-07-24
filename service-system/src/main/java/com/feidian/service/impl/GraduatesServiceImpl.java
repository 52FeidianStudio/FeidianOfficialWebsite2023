package com.feidian.service.impl;
import com.feidian.bo.GraduatesBO;
import com.feidian.dto.PageDTO;
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
}
