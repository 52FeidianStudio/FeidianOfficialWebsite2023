package com.feidian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feidian.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
}
