package com.feidian;

import com.feidian.util.serviceUtil.EmailUtil;
import com.feidian.util.serviceUtil.VerifyCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestExecutionListeners;


public class test {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        System.out.println(passwordEncoder.encode("@Soososoo123"));
    }

    @Test
    public void test02() {
        String str = VerifyCode.setVerifyCode();
        EmailUtil.sendEmail("2695946524@qq.com", str);
    }

}
