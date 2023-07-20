package com.feidian.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.feidian.mapper")
public class WebsiteFrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebsiteFrontApplication.class,args);
    }
}
