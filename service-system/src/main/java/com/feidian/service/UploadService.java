package com.feidian.service;

import com.feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface  UploadService  {
    ResponseResult uploadImage(MultipartFile image) throws IOException;
}
