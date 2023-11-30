package com.kingofboss.backend.controller.feedback;


import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *  文件上传接口
 */
@RestController
public class FileTransmitController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/file/";

    /**
     * 文件上传
     */
    @PostMapping("/api/user/feedback/attachment/upload/")
    public Map<String, String> upload(MultipartFile file) {

        System.out.println("[upload] receive request");
        Map<String, String> response = new HashMap<>();
        synchronized (FileTransmitController.class) {
            String flag = System.currentTimeMillis() + "";
            String fileName = file.getOriginalFilename();
            try {
                if (!Files.isDirectory(Paths.get(filePath))) {
                    Files.createDirectories(Paths.get(filePath));
                }
                // 文件存储形式：时间戳-文件名
                Files.write(Paths.get(filePath + flag + "-" + fileName), file.getBytes());
                System.out.println(fileName + "--上传成功");
                response.put("error_message", "");
                response.put("success", "true");
            } catch (Exception e) {
                System.err.println(fileName + "--文件上传失败");
                response.put("error_message", "File upload failed");
                response.put("success", "false");
            }
        }
        return response;
    }


    /**
     * 获取文件
     */
    @GetMapping("/api/user/feedback/attachment/{flag}/")
    public void downloadFile(@PathVariable String flag, HttpServletResponse response) {
        try {
            Path dirPath = Paths.get(filePath);
            if (!Files.isDirectory(dirPath)) {
                Files.createDirectories(dirPath);
            }
            try (Stream<Path> paths = Files.walk(dirPath)) {
                Path file = paths
                        .filter(Files::isRegularFile)
                        .filter(p -> p.getFileName().toString().contains(flag))
                        .findFirst()
                        .orElse(null);
                if (file != null) {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getFileName().toString(), "UTF-8"));
                    response.setContentType("application/octet-stream");
                    Files.copy(file, response.getOutputStream());
                    response.getOutputStream().flush();
                }
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}
