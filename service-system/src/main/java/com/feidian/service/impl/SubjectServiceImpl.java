package com.feidian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.mapper.SubjectMapper;
import com.feidian.po.Subject;
import com.feidian.service.SubjectService;
import org.springframework.stereotype.Service;

/**
 * (Subject)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:28:12
 */
@Service("subjectService")
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

}
