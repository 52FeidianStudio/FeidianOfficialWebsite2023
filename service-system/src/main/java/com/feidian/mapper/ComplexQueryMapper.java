package com.feidian.mapper;

import com.feidian.po.Register;
import com.feidian.po.User;
import com.feidian.vo.CompleteRegisterVO;
import com.feidian.vo.SectionalRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComplexQueryMapper {

    CompleteRegisterVO selectCompleteRegisterByRegisterId(Long registerId);

    List<SectionalRegisterVO> selectSectionalRegisterVOByUser(List<User> userList);

    List<SectionalRegisterVO> selectSectionalRegisterVOByRegister(List<Register> registerList);

}
