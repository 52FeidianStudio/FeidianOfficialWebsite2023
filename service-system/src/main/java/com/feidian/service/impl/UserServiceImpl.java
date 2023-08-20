package com.feidian.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.feidian.bo.LoginUser;
import com.feidian.bo.SubjectInfo;
import com.feidian.constants.SystemConstants;
import com.feidian.dto.LoginFormDTO;
import com.feidian.dto.LoginUserDTO;
import com.feidian.dto.RegisterUserDTO;
import com.feidian.dto.SubjectDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.PermissionMapper;
import com.feidian.mapper.SubjectMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.mapper.UserRoleMapper;
import com.feidian.po.User;
import com.feidian.po.UserRole;
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
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public ResponseResult registerUser(RegisterUserDTO registerUserDTO) {

        // 1.校验邮箱
        String email = registerUserDTO.getEmail();
        // 验证邮箱格式是否正确
        if (RegexUtils.isEmailInvalid(email)) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_FORMAT);
        }
        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        String code = registerUserDTO.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            throw new SystemException(HttpCodeEnum.CODE_ERR);
        }

        // 对数据进行是否已经存在的判断
        if(userMapper.isEmailExist(registerUserDTO.getEmail())){
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }
        if(userMapper.isPhoneExist(registerUserDTO.getPhone())){
            throw new SystemException(HttpCodeEnum.PHONE_EXIST);
        }

        // 对密码是否符合规范的判断
        if(!StandardPasswordUtil.isPasswordStandardized(registerUserDTO.getPassword())){
            throw new SystemException(HttpCodeEnum.PASSWORD_NOT_STANDARDIZED);
        }
        // 电话是否符合规范
        if (!phoneFormat(registerUserDTO.getPhone())) {
            throw new SystemException(HttpCodeEnum.PHONE_NOT_FORMAT);
        }


        User user = encapsulationUser(registerUserDTO);

        //存入数据库
        userMapper.insertUser(user);

        // 设置默认的角色到user_role表里
        addRole(user);
        return ResponseResult.successResult();

    }

    @Override
    public ResponseResult sendEmail(String email, HttpSession session) {
        // 确保邮箱不为空
        if (!StringUtils.hasText(email)) {
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 验证邮箱是否存在
        if(userMapper.isEmailExist(email)){
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
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, 5, TimeUnit.MINUTES);
//        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+email,code, Long.parseLong(LOGIN_CODE_KEY), TimeUnit.MINUTES);
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
        // 获取用户输入的密码
        String password = loginUserDTO.getPassword();
        // 对密码进行加密
        String encode = passwordEncoder.encode(password);

        // 创建认证令牌，包含用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
        // 执行认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 获取认证后的用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();


        // 获取用户ID并生成Token
        long id = loginUser.getUser().getId();
        String userId = String.valueOf(id);
        String token = JwtUtil.createJWT(userId);

        // 将用户信息存入Redis缓存
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 封装用户信息和Token，准备返回
        // 将LoginUser转换为UserInfoVo
        Long roleId = userRoleMapper.selectRoleIdsByUserId(id);
        UserInfoVO vo = new UserInfoVO(id, loginUser.getPermissions(),token,roleId);

        // 返回成功响应，包含用户信息和Token
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


    /**
     * 封装 RegisterUserDTO 到 User 对象
     *
     * @param registerUserDTO 待封装的 RegisterUserDTO 对象
     * @return 封装后的 User 对象
     */
    public User encapsulationUser(RegisterUserDTO registerUserDTO) {
        // 将 RegisterUserDTO 对象的属性复制到 User 对象中
        User user = BeanCopyUtils.copyProperty(registerUserDTO, User.class);

        // 获取专业名称并查询对应的学院ID和专业ID
        String subject = registerUserDTO.getSubject();
        SubjectInfo ids = subjectMapper.selectIdAndFacultyIdBySubjectName(subject);

        // 设置学院ID和专业ID的，从上面查到的集合得出来
        user.setFacultyId(ids.getFacultyId());
        user.setSubjectId(ids.getId());

        // 根据部门名称设置部门ID
//        String department = registerUserDTO.getDepartment();
//        long departmentId = 0L;
//        switch (department) {
//            case "前端":
//                departmentId = 1L;
//                break;
//            case "后端":
//                departmentId = 2L;
//                break;
//            case "ios":
//                departmentId = 3L;
//                break;
//            case "信息安全":
//                departmentId = 4L;
//                break;
//        }
        user.setDepartmentId(SystemConstants.DEFAULT_DEPARTMENT_ID);

        // 获取当前时间
        Date now = new Date();
        // 设置gmt_create字段值
        user.setCreateTime(new java.sql.Timestamp(now.getTime()));
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setIsDeleted(SystemConstants.NOT_DELETED);


        return user;
    }


    public void addRole(User user){
        // 再把刚建立的UserId找出来，因为phone字段建立索引了，所以用它来查
        long userId= userMapper.selectUserIdByPhone(user.getPhone());

        UserRole userRole= new UserRole();
        userRole.setRoleId(6L);
        userRole.setUserId(userId);
        Date now = new Date();
        userRole.setCreateTime(new java.sql.Timestamp(now.getTime()));
        userRole.setCreateBy(user.getUsername());
        userRole.setIsDeleted(SystemConstants.NOT_DELETED);
        userRoleMapper.createUserRole(userRole);
    }
}
