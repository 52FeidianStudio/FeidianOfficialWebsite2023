package com.feidian.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.feidian.bo.LoginUser;
import com.feidian.dto.LoginFormDTO;
import com.feidian.dto.LoginUserDTO;
import com.feidian.dto.RegisterUserDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.PermissionMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.mapper.UserMapperTwo;
import com.feidian.po.User;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.UserService;
import com.feidian.util.BeanCopyUtils;
import com.feidian.util.JwtUtil;
import com.feidian.util.RedisCache;
import com.feidian.util.serviceUtil.EmailUtil;
import com.feidian.util.serviceUtil.RegexUtils;
import com.feidian.util.serviceUtil.StandardPasswordUtil;
import com.feidian.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.feidian.constants.RedisConstants.LOGIN_CODE_KEY;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-07-21 10:57:50
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    UserMapperTwo userMapperTwo;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ResponseResult registerUser(RegisterUserDTO registerUserDTO) {
        // 进行非空判断
        // TODO 到时拿注释插件来优化一下
        if (!StringUtils.hasText(registerUserDTO.getUsername())) {
            throw new SystemException(HttpCodeEnum.USERNAME_NOT_NULL); // 抛出用户名不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getPassword())) {
            throw new SystemException(HttpCodeEnum.PASSWORD_NOT_NULL); // 抛出密码不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getName())) {
            throw new SystemException(HttpCodeEnum.NAME_NOT_NULL); // 抛出姓名不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getBirthday())) {
            throw new SystemException(HttpCodeEnum.BIRTHDAY_NOT_NULL); // 抛出生日不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getSex())) {
            throw new SystemException(HttpCodeEnum.INVALID_SEX); // 抛出无效性别的异常
        }
        if (registerUserDTO.getStudentId() <= 0) {
            throw new SystemException(HttpCodeEnum.INVALID_STUDENT_ID); // 抛出无效学号的异常
        }
        if (registerUserDTO.getFacultyId() <= 0) {
            throw new SystemException(HttpCodeEnum.FACULTY_NOT_NULL); // 抛出学院不能为空的异常
        }
        if (registerUserDTO.getDepartmentId() <= 0) {
            throw new SystemException(HttpCodeEnum.DEPARTMENT_NOT_NULL); // 抛出部门不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getNationality())) {
            throw new SystemException(HttpCodeEnum.NATIONALITY_NOT_NULL); // 抛出民族不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getPhone())) {
            throw new SystemException(HttpCodeEnum.PHONE_NOT_NULL); // 抛出联系电话不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getEmail())) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL); // 抛出邮箱不能为空的异常
        }
        if (!StringUtils.hasText(registerUserDTO.getQq())) {
            throw new SystemException(HttpCodeEnum.QQ_NOT_NULL); // 抛出QQ号不能为空的异常
        }


        // 对数据进行是否已经存在的判断
        if(userMapperTwo.isEmailExist(registerUserDTO.getEmail())){
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }
        if(userMapperTwo.isPhoneExist(registerUserDTO.getPhone())){
            throw new SystemException(HttpCodeEnum.PHONE_EXIST);
        }

        // 对密码是否符合规范的判断
        if(!StandardPasswordUtil.isPasswordStandardized(registerUserDTO.getPassword())){
            throw new SystemException(HttpCodeEnum.PASSWORD_NOT_STANDARDIZED);
        }
        if (!phoneFormat(registerUserDTO.getPhone())) {
            throw new SystemException(HttpCodeEnum.PHONE_NOT_FORMAT);
        }

        // 验证邮箱
        EmailUtil.sendEmail(registerUserDTO.getEmail());


        User user= BeanCopyUtils.copyProperty(registerUserDTO,User.class);
        // 获取当前时间
        Date now = new Date();
        // 设置gmt_create字段值
        user.setCreateTime(new java.sql.Timestamp(now.getTime()));
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        userMapperTwo.insertUser(user);

        return ResponseResult.successResult();

    }

    @Override
    public ResponseResult sendEmail(String email, HttpSession session) {
        // 确保邮箱不为空
        if (!StringUtils.hasText(email)) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 验证邮箱是否存在
        if(userMapperTwo.isEmailExist(email)){
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }
        // 验证邮箱格式是否正确
        if (RegexUtils.isEmailInvalid(email)) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_FORMAT);
        }
        // 生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 发送验证吗
        EmailUtil.sendEmail(email,code);

        // 保存验证吗到redis
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+email,code, Long.parseLong(LOGIN_CODE_KEY), TimeUnit.MINUTES);
        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult verifyEmail(LoginFormDTO loginForm, HttpSession session) {
        // 1.校验邮箱
        String email = loginForm.getEmail();
        // 验证邮箱格式是否正确
        if (RegexUtils.isEmailInvalid(email)) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_FORMAT);
        }
        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            throw new SystemException(HttpCodeEnum.CODE_ERR);
        }

        return ResponseResult.successResult();
    }

    @Override
    public ResponseResult login(LoginUserDTO loginUserDTO) {
        String password = loginUserDTO.getPassword();
        String encode = passwordEncoder.encode(password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getUserName(),loginUserDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 获取userid 生成token
        User user =(User) authenticate.getPrincipal();
        List<String> permissionKeyList =  permissionMapper.selectPermsByUserId(user.getId());
        LoginUser loginUser=new LoginUser(user,permissionKeyList);
        long id = loginUser.getUser().getId();
        String userId = String.valueOf(id);
        String jwt = JwtUtil.createJWT(userId);
        // 把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        // 把token和userinfo封装 返回
        // 把User转换成UserInfoVo

        UserInfoVO vo = new UserInfoVO(loginUser,jwt);

        return ResponseResult.successResult(vo);
    }



    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.successResult();
    }




    public static boolean phoneFormat(String phone){
        final Pattern regex = Pattern.compile("^1\\d{10}$");
        boolean isMatch = regex.matcher(phone).matches();
        return isMatch;
    }
}
