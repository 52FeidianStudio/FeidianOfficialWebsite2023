package com.feidian.mapper;

import com.feidian.po.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RolePermissionMapper  {
    List<RolePermission> selectByRoleId(List<Long> roleIds);
}
