package com.feidian.service.impl;


import com.feidian.bo.LoginUser;
import com.feidian.po.Permission;
import com.feidian.po.RolePermission;
import com.feidian.po.User;
import com.feidian.po.UserRole;
import com.feidian.mapper.PermissionMapper;
import com.feidian.mapper.RolePermissionMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        User user = userMapper.selectUserByUsername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //查询对应的权限信息并将其封装成一个list集合
        Long userId = user.getId();
        List<String> authenticationList = new ArrayList<>();

        // 查询权限菜单
        List<String> permsList = permissionMapper.selectPermsByUserId(userId);

        // 用户权限菜单
        for (String permission : permsList) {
            authenticationList.add(permission);
        }

        //把数据封装成UserDetails返回
        LoginUser loginUser = new LoginUser(user, authenticationList);
        return loginUser;
    }
}