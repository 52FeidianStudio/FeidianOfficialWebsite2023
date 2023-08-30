package com.feidian.controller;

import com.feidian.responseResult.ResponseResult;
import com.feidian.util.serviceUtil.AliyunOSSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

    @PostMapping("/testAliyun")
    public ResponseResult testAliyun(@RequestPart MultipartFile imageFile){
        return AliyunOSSUtil.uploadImage(imageFile);
    }
}
