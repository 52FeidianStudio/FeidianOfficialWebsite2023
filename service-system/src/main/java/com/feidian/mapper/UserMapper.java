package com.feidian.mapper;


import com.feidian.po.User;
import com.feidian.po.UserRole;
import com.feidian.vo.QueryCategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //   根据UserId查询User
    User selectUserByUserId(@Param("userId") Long userId);

    //根据年级或者专业查询出UserList
    List<User> selectUserListByGradeNameOrSubjectId(@Param("gradeName") String gradeName,@Param("subjectId") Long subjectId);


    //根据registerId查询User
    User selectUserByRegisterId(Long registerId);

    boolean isEmailExist(String email);

    boolean isPhoneExist(String phone);

    User selectUserByUsername(String username);

    void insertUser(User user);

    long selectUserIdByPhone(String phone);

    List<QueryCategoryVO> selectGradeName(List<Long> list);

    List<QueryCategoryVO> selectSubjectIdAndSubjectName(List<Long> list);

    boolean isUsernameExist(String username);
}
