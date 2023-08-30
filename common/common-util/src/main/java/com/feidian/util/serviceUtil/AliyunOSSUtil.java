package com.feidian.util.serviceUtil;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class AliyunOSSUtil {
    //阿里云域名
    public static final String ALIYUN_DOMAIN = "https://feidian-official-website2023.oss-cn-hangzhou.aliyuncs.com/";

    // 支持的图片文件格式
    private static final String[] SUPPORTED_IMAGE_EXTENSIONS = {"jpg", "jpeg", "png"};
    //图片文件Url长度上限
    private static final int IMG_URL_MAX_LENGTH = 255;

    public static ResponseResult uploadImage(MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            return ResponseResult.errorResult(400, "未选择文件");
        }
        //根据文件拓展名判断文件是否合法
        String fileExtension = getFileExtension(imageFile.getOriginalFilename());
        boolean isValidExtension = false;
        for (String allowedFileFormat : Arrays.asList(SUPPORTED_IMAGE_EXTENSIONS)) {
            if (fileExtension.equals(allowedFileFormat)) {
                isValidExtension = true;
                break;
            }
        }
        if (!isValidExtension) {
            return ResponseResult.errorResult(400, "文件格式不支持");
        }

        //生成文件名
        String fileName = createFilename(imageFile).getData().toString();
        //地域节点
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5tKpuwHLYLXH7LArupGv";
        String accessKeySecret = "T0vwQUkNvY8sQPGGe7rVXdJRslv5VQ";

        //OSS客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        //创建OSS链接
        try {
            ossClient.putObject(
                    "feidian-official-website2023",//仓库名
                    fileName,//文件名
                    imageFile.getInputStream()
            );
        } catch (IOException e) {
            return ResponseResult.errorResult(400,"上传文件失败:" + e.getMessage());
        }
        ossClient.shutdown();
        return ResponseResult.successResult(ALIYUN_DOMAIN + fileName);
    }

    public static ResponseResult createFilename(MultipartFile imageFile) {
        // 将原始文件名按照路径分隔符进行拆分
        String[] pathSegments = imageFile.getOriginalFilename().split("[\\\\/]");
        // 获取最后一个路径分段（即文件名部分）
        String fileName = pathSegments[pathSegments.length - 1];
        // 生成保存文件的路径，利用UUID生成唯一标识
        String finalFileName = UUID.randomUUID() + "-" + fileName;

        if (IMG_URL_MAX_LENGTH < finalFileName.length()) {
            return ResponseResult.errorResult(400, "头像URL超出长度限制");
        }
        return ResponseResult.successResult(finalFileName);
    }

    //获取文件拓展名
    public static String getFileExtension(String fileName) {
        String extension = "";

        // 检查文件路径是否为空
        if (fileName != null && !fileName.isEmpty()) {
            // 获取最后一个点的索引，表示文件名和扩展名的分隔位置
            int dotIndex = fileName.lastIndexOf('.');

            // 检查是否有有效的扩展名
            if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
                // 提取扩展名部分
                extension = fileName.substring(dotIndex + 1);
            }
        }
        return extension;
    }

}
