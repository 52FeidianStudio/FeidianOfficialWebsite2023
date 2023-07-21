package com.feidian.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.mapper.GraduatesMapper;
import com.feidian.po.Graduates;
import com.feidian.service.GraduatesService;
import org.springframework.stereotype.Service;

/**
 * (Graduates)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
@Service("graduatesService")
public class GraduatesServiceImpl extends ServiceImpl<GraduatesMapper, Graduates> implements GraduatesService {

}
