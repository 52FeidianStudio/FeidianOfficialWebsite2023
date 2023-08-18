package com.feidian.mapper;

import com.feidian.po.Register;
import com.feidian.po.User;
import com.feidian.vo.CompleteRegisterVO;
import com.feidian.vo.QueryCategoryVO;
import com.feidian.vo.SectionalRegisterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegisterMapper {
    //查询所有报名表
    List<Register> selectAllRegister();

    // 根据用户ID查询报名表
    Register selectRegisterByUserId(@Param("userId") Long userId);

    // 根据报名表ID查询报名表
    Register selectRegisterByRegisterId(@Param("registerId") Long registerId);


    // 根据申请组别或报名表状态查询报名表
    List<Register> selectByDesireDepartmentIdOrStatus(@Param("desireDepartmentId") Long desireDepartmentId, @Param("status") String status);


    // 添加报名表
    int insertRegister(Register register);

    // 更新报名表状态
    int updateStatus(Register register);

    // 更新报名表内容
    int updateContent(Register register);


    //   多表联查 根据registerId查询User和Register并封装成一个CompleteRegisterVO
    CompleteRegisterVO selectCompleteRegisterVOByRegisterId(Long registerId);

    //   多表联查 根据userId查询User和Register并封装成一个SectionalRegisterVO
    List<SectionalRegisterVO> selectSectionalRegisterVOByUser(List<Long> userIdList);

    //   多表联查 根据registerId查询User和Register并封装成一个SectionalRegisterVO
    List<SectionalRegisterVO> selectSectionalRegisterVOByRegister(List<Long> registerIdList);


    List<QueryCategoryVO> selectDesireDepartmentIdAndDepartmentName();

    List<QueryCategoryVO> selectStatus();


}
