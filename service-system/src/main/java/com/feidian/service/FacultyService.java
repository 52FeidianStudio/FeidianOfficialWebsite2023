package com.feidian.service;


import com.feidian.responseResult.ResponseResult;

/**
 * (Faculty)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 11:20:09
 */
public interface FacultyService  {

    ResponseResult getAllName();

    ResponseResult getSubjectNameByFaculty(String name);
}
