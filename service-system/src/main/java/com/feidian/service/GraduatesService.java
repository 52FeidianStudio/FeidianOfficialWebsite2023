package com.feidian.service;


import com.feidian.dto.PageDTO;
import com.feidian.responseResult.ResponseResult;

/**
 * (Graduates)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
public interface GraduatesService  {

    ResponseResult getMessage(PageDTO pageDTO);
}
