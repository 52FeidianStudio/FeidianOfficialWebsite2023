package com.feidian.service.impl;


import com.feidian.constants.SystemConstants;
import com.feidian.dto.AddRoleDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.UserMapper;
import com.feidian.mapper.UserRoleMapper;
import com.feidian.po.User;
import com.feidian.po.UserRole;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.RoleService;
import com.feidian.util.BeanCopyUtils;
import com.feidian.util.serviceUtil.EmailUtil;
import com.feidian.util.serviceUtil.StandardPasswordUtil;
import com.feidian.vo.UserRoleVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 11:27:04
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult addRole(AddRoleDTO dto) {
        // 对数据进行是否已经存在的判断
        if(userMapper.isEmailExist(dto.getEmail())){
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }
        if(userMapper.isPhoneExist(dto.getPhone())){
            throw new SystemException(HttpCodeEnum.PHONE_EXIST);
        }

        // 对密码是否符合规范的判断
        if(!StandardPasswordUtil.isPasswordStandardized(dto.getPassword())){
            throw new SystemException(HttpCodeEnum.PASSWORD_NOT_STANDARDIZED);
        }
        if (!phoneFormat(dto.getPhone())) {
            throw new SystemException(HttpCodeEnum.PHONE_NOT_FORMAT);
        }


        User user= BeanCopyUtils.copyProperty(dto,User.class);
        // 获取当前时间
        Date now = new Date();
        // 设置gmt_create字段值
        user.setCreateTime(new java.sql.Timestamp(now.getTime()));
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        userMapper.insertUser(user);
        // 再把刚建立的UserId找出来，因为phone字段建立索引了，所以用它来查
        long userId= userMapper.selectUserIdByPhone(dto.getPhone());

        UserRole userRole= new UserRole();
        userRole.setRoleId(dto.getRoleId());
        userRole.setUserId(userId);
        userRole.setCreateTime(new java.sql.Timestamp(now.getTime()));
        userRole.setCreateBy(dto.getUsername());
        userRole.setIsDeleted(SystemConstants.NOT_DELETED);
        userRoleMapper.createUserRole(userRole);

        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult viewAllRoleInformation() {
        // 从userRole表中查询所有的user和Role
        List<UserRoleVO> userRoleList = userRoleMapper.selectAllUserRole();

        return ResponseResult.successResult(userRoleList);
    }

    public static boolean phoneFormat(String phone){
        final Pattern regex = Pattern.compile("^1\\d{10}$");
        boolean isMatch = regex.matcher(phone).matches();
        return isMatch;
    }



}
