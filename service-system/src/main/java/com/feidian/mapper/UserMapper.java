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

    //   根据register查询出一个User
    User selectUserByRegisterId(Long registerId);

}
