package com.feidian.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RegisterMapper  {

    //  TODO
    //   registerMapper根据用户Id查询报名表
    //   registerMapper根据申请组别查询报名表
    //   registerMapper根据报名表状态查询报名表
    //   registerMapper添加register
    //   registerMapper更新status
    //   registerMapper更新内容
    //   userRoleMapper更新用户角色
    //   userMapper根据UserId查询User
    //   userMapper根据当前SecurityContext里的UserId查询用户
    //   userMapper根据年级查询出一个UserList
    //   userMapper根据专业查询出一个UserList

    //  TODO
    //   多表联查 根据registerId查询User和Register并封装成一个CompleteRegisterVO
    //   多表联查 根据userId查询User和Register并封装成一个SectionalRegisterVO
    //   多表联查 根据registerId查询User和Register并封装成一个SectionalRegisterVO

}
