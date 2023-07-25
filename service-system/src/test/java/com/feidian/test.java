package com.feidian;

import com.feidian.util.serviceUtil.EmailUtil;
import com.feidian.util.serviceUtil.VerifyCode;
import org.junit.jupiter.api.Test;


public class test {

    @Test
    public void test(){
        String str = "$2a$10$OgBm1.98uj.9TznI6p/jsuXOjXjmgfirTRukyxMNHPguPYQ0.EjSC(String)";
        System.out.println(str.length());
    }

    @Test
    public void test02(){
        String str = VerifyCode.setVerifyCode();
        EmailUtil.sendEmail("2695946524@qq.com",str);
    }
}
