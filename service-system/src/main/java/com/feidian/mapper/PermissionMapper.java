package com.feidian.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper  {
    List<String> selectPermsByUserId(Long id);
}
