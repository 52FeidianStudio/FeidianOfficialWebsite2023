package com.feidian.mapper;


import com.feidian.bo.GraduatesBO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Graduates)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-21 11:23:32
 */
@Mapper
public interface GraduatesMapper{

    List<GraduatesBO> getGraduatesMessage();
}

