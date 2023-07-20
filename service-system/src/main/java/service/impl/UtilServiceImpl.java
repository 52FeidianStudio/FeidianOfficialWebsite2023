package service.impl;

import com.feidian.responseResult.ResponseResult;
import com.feidian.service.UtilService;
import com.feidian.util.EmailUtil;
import com.feidian.util.RedisCache;
import com.feidian.util.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.feidian.enums.HttpCodeEnum.REQUIRE_USERNAME;

@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult sendVerifyCode(String emailAddress, String label) {
        if (label == null){
            return ResponseResult.errorResult(REQUIRE_USERNAME);
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
