package com.feidian.service.impl;

import com.feidian.dto.ForgetPasswordDTO;
import com.feidian.enums.HttpCodeEnum;
import com.feidian.exception.SystemException;
import com.feidian.mapper.ForgetPasswordMapper;
import com.feidian.mapper.UserMapper;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.FacultyService;
import com.feidian.service.ForgetPasswordService;
import com.feidian.util.serviceUtil.VerifyCode;
import io.micrometer.core.instrument.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Value("${spring.mail.username}")
    private String from;   // 邮件发送人

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ForgetPasswordMapper forgetPasswordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public ResponseResult forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {

        String address = forgetPasswordDTO.getAddress();
        String username = forgetPasswordDTO.getUsername();
        //验证用户名和邮箱是否匹配
        //写一个通过用户名查邮箱的接口
        if(StringUtils.isBlank(username)){
            throw new SystemException(HttpCodeEnum.USERNAME_NOT_NULL);
        }else if(StringUtils.isBlank(address)){
            throw new SystemException(HttpCodeEnum.EMAIL_NOT_NULL);
        }
        // 对数据进行是否已经存在的判断
        if(userMapper.isEmailExist(address)){
            throw new SystemException(HttpCodeEnum.EMAIL_EXIST);
        }
        String email = forgetPasswordMapper.getEmailByUsername(username);
        //TODO 判断用户名邮箱是否匹配，发送验证码，验证验证码是否正确
        if(!email.equals(address)){
            throw new SystemException(HttpCodeEnum.USERNAME_EMAIL_NOT_MATCH);
        }
        //返回验证码
        return sendMsg(address);
    }

    public ResponseResult sendMsg(String address) {
        String subject = "忘记密码邮箱验证码";
        if (StringUtils.isNotEmpty(address)) {
            String code = VerifyCode.setVerifyCode();
            String context = "您的验证码验证码为: " + code + ",五分钟内有效，请妥善保管!";
            log.info("code={}", code);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(from);
            mailMessage.setTo(address);
            mailMessage.setSubject(subject);
            mailMessage.setText(context);
            // 真正的发送邮件操作
            mailSender.send(mailMessage);
            // 验证码由保存到session 优化为 缓存到Redis中，并且设置验证码的有效时间为 5分钟
            redisTemplate.opsForValue().set(address, code, 5, TimeUnit.MINUTES);
            return ResponseResult.successResult("验证码发送成功，请及时查看!");
        }
        return ResponseResult.errorResult(505,"验证码发送失败，请重新输入!");

    }
}



