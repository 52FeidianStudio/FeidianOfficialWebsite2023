package feidian.util.serviceUtil;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ReceivingFileUtil {

    public static String saveFile(MultipartFile file, String uploadDir) {

        if (file.isEmpty()) {
            return "未选择文件";
        }

        try {
            // 定义保存路径
            // 创建保存目录（如果不存在）
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成保存文件的路径，利用UUID生成唯一标识
            String filePath = uploadDir + UUID.randomUUID() + file.getOriginalFilename();

            // 保存文件
            file.transferTo(new File(filePath));
            return filePath;

        } catch (IOException e) {
            return "上传失败" + e.getMessage();
        }
    }

}
