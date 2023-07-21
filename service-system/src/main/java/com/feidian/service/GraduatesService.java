package com.feidian.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.feidian.dto.PageDTO;
import com.feidian.po.Graduates;
import feidian.responseResult.ResponseResult;


/**
 * (Graduates)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
public interface GraduatesService extends IService<Graduates> {

    ResponseResult getMessage(PageDTO pageDTO);
}
