package com.feidian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.mapper.FacultyMapper;
import com.feidian.po.Faculty;
import com.feidian.service.FacultyService;
import org.springframework.stereotype.Service;

/**
 * (Faculty)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:20:09
 */
@Service("facultyService")
public class FacultyServiceImpl extends ServiceImpl<FacultyMapper, Faculty> implements FacultyService {

}
