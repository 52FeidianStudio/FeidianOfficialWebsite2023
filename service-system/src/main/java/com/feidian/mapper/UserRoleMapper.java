package com.feidian.mapper;


import com.feidian.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper  {

    // 更新用户角色
    int updateUserRole(UserRole userRole);

}
