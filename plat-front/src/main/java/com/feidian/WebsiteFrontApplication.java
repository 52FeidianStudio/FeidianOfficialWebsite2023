package com.feidian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.feidian")
@MapperScan("com.feidian.mapper")
public class WebsiteFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsiteFrontApplication.class,args);
    }
}
