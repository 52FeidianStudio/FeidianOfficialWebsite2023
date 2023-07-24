package com.feidian.mapper;


import com.feidian.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //   根据UserId查询User
    User selectUserByUserId(@Param("userId") Long userId);

    //   根据年级查询出一个UserList
    List<User> selectUserListByGradeName(@Param("gradeName") String gradeName);

    //   根据专业查询出一个UserList
    List<User> selectUserListBySubjectId(@Param("subjectId") Long subjectId);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);

    User selectUserByUsername(String username);

    void insertUser(User user);


    //  TODO
    //   多表联查 根据registerId查询User和Register并封装成一个CompleteRegisterVO
    //   多表联查 根据userId查询User和Register并封装成一个SectionalRegisterVO
    //   多表联查 根据registerId查询User和Register并封装成一个SectionalRegisterVO

}
