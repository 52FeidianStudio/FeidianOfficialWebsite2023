package com.feidian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feidian.po.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
}
