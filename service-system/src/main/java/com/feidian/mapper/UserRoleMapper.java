package com.feidian.mapper;


import com.feidian.po.UserRole;
import com.feidian.vo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleMapper  {

    // 更新用户角色
    int updateUserRole(UserRole userRole);

    // 创建新的权限角色
    void createUserRole(UserRole userRole);

    List<UserRoleVO> selectAllUserRole();

}
