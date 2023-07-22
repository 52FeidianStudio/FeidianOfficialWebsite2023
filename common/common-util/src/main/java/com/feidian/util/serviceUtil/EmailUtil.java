package com.feidian.util.serviceUtil;

import org.apache.commons.mail.HtmlEmail;

public class EmailUtil {

    public static void sendEmail(String emailAddress) {

        try {
            HtmlEmail send = new HtmlEmail();
            //设置发送邮箱的host 默认值
            send.setHostName("smtp.qq.com");
            //配置发送邮箱和邮箱授权码
            send.setAuthentication("2695946524@qq.com","xsgewhanpvhudhdj");
            //配置发送方
            send.setFrom("2695946524@qq.com");
            //配置接收人
            send.addTo(emailAddress);
            //设置邮箱主题
            send.setSubject("test");
            //具体的发送消息
            send.setMsg("欢迎使用邮箱验证本系统！");
            send.setCharset("UTF-8");
            send.send();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sendEmail(String emailAddress, String verifyCode) {

        try {
            HtmlEmail send = new HtmlEmail();
            //设置发送邮箱的host 默认值
            send.setHostName("smtp.qq.com");
            //配置发送邮箱和邮箱授权码
            send.setAuthentication("2695946524@qq.com","xsgewhanpvhudhdj");
            //配置发送方
            send.setFrom("2695946524@qq.com");
            //配置接收人
            send.addTo(emailAddress);
            //设置邮箱主题
            send.setSubject("test");
            //具体的发送消息
            send.setMsg("欢迎使用邮箱验证本系统！");
            send.setMsg("这是您的验证码，请妥善保管:"+verifyCode);
            send.setCharset("UTF-8");
            send.send();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
