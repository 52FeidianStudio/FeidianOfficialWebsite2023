package feidian.util.serviceUtil;

import feidian.responseResult.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class FileUploadUtil {

    // 支持的图片文件格式
    private static final String[] SUPPORTED_IMAGE_EXTENSIONS = {"jpg", "jpeg", "png"};
    //图片文件Url长度上限
    private static final int IMG_URL_MAX_LENGTH = 255;

    public static ResponseResult uploadAvatar(MultipartFile avatarFile){

        if (avatarFile.isEmpty()) {
            return ResponseResult.errorResult(400,"未选择文件");
        }

        //根据文件拓展名判断文件是否合法
        String fileExtension = getFileExtension(avatarFile.getOriginalFilename());
        boolean isValidExtension = false;
        for (String allowedFileFormat : Arrays.asList(SUPPORTED_IMAGE_EXTENSIONS)){
            if (fileExtension.equals(allowedFileFormat)){
                isValidExtension = true;
                break;
            }
        }
        if(!isValidExtension){
            return ResponseResult.errorResult(400,"文件格式不支持");
        }

        //文件保存在服务器上的目录地址
        String uploadDir = "http://182.254.242.96:3333//www//wwwroot//FeidianOfficialWebsite2023//image//";

        try {
            // 定义保存路径
            // 创建保存目录（如果不存在）
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成保存文件的路径，利用UUID生成唯一标识
            String filePath = uploadDir + UUID.randomUUID() + avatarFile.getOriginalFilename();

            if (filePath.length() > IMG_URL_MAX_LENGTH) {
                return ResponseResult.errorResult(400,"头像URL超出长度限制");
            }

            // 保存文件
            avatarFile.transferTo(new File(filePath));
            return ResponseResult.successResult("filePath");

        } catch (IOException e) {
            return ResponseResult.errorResult(400,"上传失败" + e.getMessage());
        }
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
