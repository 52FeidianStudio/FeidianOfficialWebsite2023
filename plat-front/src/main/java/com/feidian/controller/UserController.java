package com.feidian.controller;

import com.feidian.dto.LoginFormDTO;
import com.feidian.dto.LoginUserDTO;
import com.feidian.dto.RegisterUserDTO;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * @param registerUserDTO 注册用户信息的数据传输对象
     * @return 返回 ResponseResult 对象，表示注册结果
     */
    @PostMapping("/register")
    public ResponseResult registerUser(@Valid @RequestBody RegisterUserDTO registerUserDTO){
        return userService.registerUser(registerUserDTO);
    }

    /**
     * 发送邮箱验证码接口
     * @param email 邮箱地址
     * @param session HttpSession对象，用于存储验证码等信息
     * @return 返回 ResponseResult 对象，表示发送邮箱验证码结果
     */
    @PostMapping("/sendEmail")
    public ResponseResult sendEmail(@RequestParam("email") String email, HttpSession session){
        return userService.sendEmail(email, session);
    }

    /**
     * 验证邮箱验证码接口
     * @param loginForm 登录表单信息的数据传输对象
     * @param session HttpSession对象，用于获取存储的验证码等信息
     * @return 返回 ResponseResult 对象，表示验证邮箱验证码结果
     */
    @PostMapping("/verifyEmail")
    public ResponseResult verifyEmail(@RequestBody LoginFormDTO loginForm, HttpSession session){
        return userService.verifyEmail(loginForm, session);
    }

    /**
     * 用户登录接口
     * @param loginUserDTO 登录用户信息的数据传输对象
     * @return 返回 ResponseResult 对象，表示登录结果
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginUserDTO loginUserDTO){
        return userService.login(loginUserDTO);
    }

    /**
     * 用户注销接口
     * @return 返回 ResponseResult 对象，表示注销结果
     */
    @PostMapping("/logout")
    public ResponseResult logout(){
        return userService.logout();
    }
}
