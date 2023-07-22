package com.feidian.controller;


import com.feidian.responseResult.ResponseResult;
import com.feidian.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/img")
    public ResponseResult uploadImage(@RequestBody MultipartFile image) throws IOException {
        return uploadService.uploadImage(image);
    }
}
