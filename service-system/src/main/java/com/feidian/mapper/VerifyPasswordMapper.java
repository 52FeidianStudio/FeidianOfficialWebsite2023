package com.feidian.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VerifyPasswordMapper {
    void updatePassword(String password, String address);
}
