package com.feidian.mapper;

import com.feidian.bo.GraduatesBO;
import com.feidian.po.Graduates;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * (Graduates)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
@Mapper
@Repository
public interface GraduatesMapper  {
    void insertGraduate(Graduates graduate);

    Graduates getGraduateById(Long id);

    void updateGraduate(Graduates graduate);


    List<GraduatesBO> getGraduatesMessage();
}

