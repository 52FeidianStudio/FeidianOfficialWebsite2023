package feidian.util.serviceUtil;

import com.feidian.vo.FileVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

//给前端返回文件时的工具
public class UploadingFileUtil {

    public static FileVO getFileVideo(String dataUrl) throws IOException {
        try {
            // 1. 创建一个 URI 对象来处理文件路径
            String fileUrl = "file://" + dataUrl;
            URI uri = new URI(fileUrl);

            // 2. 根据 URI 获取文件路径
            String filePath = uri.getPath();

            // 3. 根据文件路径获取要返回的编码后的视频文件
            byte[] fileBytes = encodeFileToBase64(filePath);

            // 4. 检查文件是否存在
            if (fileBytes == null) {
                return new FileVO("文件不存在");
            }

            //5.设置请求头并返回编码后的视频文件字节数组
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("video/mp4"));
            headers.setContentLength(fileBytes.length);

            return new FileVO(200, "传输成功", fileBytes);

        } catch (URISyntaxException e) {
            // 处理 URI 解析异常
            e.printStackTrace();
            return new FileVO(401, "URI解析异常");
        }

    }

    public static FileVO getFileImage(String coverUrl) throws URISyntaxException, IOException {
        // 1. 创建一个 URI 对象来处理文件路径
        String fileUrl = "file://" + coverUrl;
        URI uri = new URI(fileUrl);


        // 2. 根据 URI 获取文件路径
        String filePath = uri.getPath();

        // 3. 根据文件路径获取要返回的编码后的图片文件
        String imagePath = filePath; // 替换为实际的图片路径
        byte[] fileBytes = encodeFileToBase64(imagePath);


        //4. 获取图片文件的扩展名并设置设置HTTP响应头
        String extension = getFileExtension(coverUrl);
        HttpHeaders headers = new HttpHeaders();

        if (extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))) {
            MediaType mediaType = MediaTypeFactory.getMediaType(extension).orElse(MediaType.APPLICATION_OCTET_STREAM);

            headers.setContentType(mediaType);
            headers.setContentLength(fileBytes.length);

        }
        //5. 返回图片文件作为HTTP响应主体内容
        return new FileVO(200, "传输成功", fileBytes);
    }

    //根据文件路径获取文件内容编码后返回byte[]
    public static byte[] encodeFileToBase64 (String filePath) throws IOException {
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            byte[] base64ImageBytes = Base64.getEncoder().encode(fileBytes);
            return base64ImageBytes;
        }

    //获取文件拓展名
    public static String getFileExtension (String filePath){
            String extension = "";

            // 检查文件路径是否为空
            if (filePath != null && !filePath.isEmpty()) {
                // 获取最后一个点的索引，表示文件名和扩展名的分隔位置
                int dotIndex = filePath.lastIndexOf('.');

                // 检查是否有有效的扩展名
                if (dotIndex >= 0 && dotIndex < filePath.length() - 1) {
                    // 提取扩展名部分
                    extension = filePath.substring(dotIndex + 1);
                }
            }
            return extension;
        }

    }

