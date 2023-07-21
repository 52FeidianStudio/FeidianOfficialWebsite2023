package com.feidian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.feidian")
//@PropertySource("classpath:service-system.yml")
@MapperScan("com.feidian.mapper")
public class WebsiteFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsiteFrontApplication.class,args);
    }
}
