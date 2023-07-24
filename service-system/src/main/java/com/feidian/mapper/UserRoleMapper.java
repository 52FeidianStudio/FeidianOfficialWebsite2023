package com.feidian.mapper;


import com.feidian.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleMapper  {

    // 更新用户角色
    int updateUserRole(UserRole userRole);

    List<UserRole> selectByUserId(@Param("userId") Long userId);

}
