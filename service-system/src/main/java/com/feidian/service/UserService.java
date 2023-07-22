package com.feidian.service;


import com.feidian.dto.LoginFormDTO;
import com.feidian.dto.LoginUserDTO;
import com.feidian.dto.RegisterUserDTO;
import com.feidian.responseResult.ResponseResult;

import javax.servlet.http.HttpSession;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2023-07-21 10:57:50
 */
public interface UserService  {

    ResponseResult registerUser(RegisterUserDTO registerUserDTO);

    ResponseResult sendEmail(String email, HttpSession session);

    ResponseResult verifyEmail(LoginFormDTO loginForm, HttpSession session);

    ResponseResult login(LoginUserDTO loginUserDTO);

    ResponseResult logout();
}
