package com.feidian.mapper;

import com.feidian.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapperTwo {

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);

    User selectUserByUsername(String username);

    void insertUser(User user);

}
