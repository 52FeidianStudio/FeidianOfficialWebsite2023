package com.megumi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feidian.mapper.UserMapper;
import com.feidian.po.User;
import com.feidian.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 10:57:50
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
