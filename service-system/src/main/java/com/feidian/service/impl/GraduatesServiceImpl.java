package com.feidian.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.dto.PageDTO;
import com.feidian.mapper.GraduatesMapper;
import com.feidian.po.Graduates;
import com.feidian.service.GraduatesService;
import feidian.responseResult.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Graduates)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
@Service("graduatesService")
public class GraduatesServiceImpl extends ServiceImpl<GraduatesMapper, Graduates> implements GraduatesService {

    @Override
    public ResponseResult getMessage(PageDTO pageDTO) {

        Page<Graduates> page = new Page<>(pageDTO.getPageNum(),pageDTO.getPageSize());
        page(page);
        List<Graduates> records = page.getRecords();
        //TODO 还没封装
        return ResponseResult.successResult(records);
    }
}
