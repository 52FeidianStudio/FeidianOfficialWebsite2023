package com.feidian.mapper;

import com.feidian.dto.FrontUserDTO;
import com.feidian.dto.FrontUserRegisterDTO;
import com.feidian.vo.FrontUserRegisterVO;
import com.feidian.vo.FrontUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FrontUserMapper {
    void updateUser(FrontUserDTO frontUserDTO);
    void updateUserRegister(FrontUserRegisterDTO frontUserRegisterDTO);

     FrontUserVO getMessageById(Long userId);

    Long getDIdByName(String departmentName);

    Long getFIdByName(String facultyName);

    Long getSIdByName(String facultyName);

    String getStatusById(Long userId);
}
