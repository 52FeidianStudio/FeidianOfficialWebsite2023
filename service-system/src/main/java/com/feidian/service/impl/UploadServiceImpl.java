package com.feidian.service.impl;
import com.feidian.responseResult.ResponseResult;
import com.feidian.service.UploadService;
import com.feidian.util.serviceUtil.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImage(MultipartFile image) throws IOException {
        return FileUploadUtil.uploadAvatar(image);
    }
}
