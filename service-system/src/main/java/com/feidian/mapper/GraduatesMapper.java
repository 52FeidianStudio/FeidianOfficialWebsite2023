package com.feidian.mapper;

import com.feidian.po.Graduates;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Graduates)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
public interface GraduatesMapper  {
    void insertGraduate(Graduates graduate);

    Graduates getGraduateById(Long id);

    void updateGraduate(Graduates graduate);



}

