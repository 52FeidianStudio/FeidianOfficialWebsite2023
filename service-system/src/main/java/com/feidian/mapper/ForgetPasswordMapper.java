package com.feidian.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ForgetPasswordMapper {
    String getEmailByUsername(String username);
}
