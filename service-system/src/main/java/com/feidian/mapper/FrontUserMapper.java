package com.feidian.mapper;

import com.feidian.dto.FrontUserDTO;
import com.feidian.vo.FrontUserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FrontUserMapper {
    void updateUser(FrontUserDTO frontUserDTO);

     FrontUserVO getMessageById(Long userId);

    Long getDIdByName(String departmentName);

    Long getFIdByName(String facultyName);

    Long getSIdByName(String facultyName);
}
