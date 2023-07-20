package service.impl;


import feidian.enums.HttpCodeEnum;
import feidian.responseResult.ResponseResult;
import feidian.util.RedisCache;
import feidian.util.serviceUtil.EmailUtil;
import feidian.util.serviceUtil.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UtilService;


@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult sendVerifyCode(String emailAddress, String label) {
        if (label == null){
            return ResponseResult.errorResult(HttpCodeEnum.REQUIRE_USERNAME);
        }
        String regexEmailAddress = "\\w+@[\\w&&[^_]]{2,7}(\\.[a-zA-Z]{2,4}){1,3}";
        String verifyCode = new VerifyCode().setVerifyCode();

        redisCache.setCacheObject(label+ "verifyCode:",verifyCode);

        if (!emailAddress.matches(regexEmailAddress)) {
            return ResponseResult.errorResult(403,"邮箱格式不正确");
        }

        //发送验证码
        EmailUtil.sendEmail(emailAddress, verifyCode);

        return ResponseResult.successResult(200,"验证码发送成功");
    }

}
