package com.kingofboss.backend.controller.serverstatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
public class GetCmdController {
//    @PostMapping("/api/user/server/cmd/")
//    String getCmd(@RequestParam("content") String ip)  {
//
//        // 拼接ping命令
//         String cmd = "ping -c 5 -w 5 " + ip;
//
//        String[] cmdA = new String[] { "/bin/sh", "-c", cmd };
////        String[] cmdA = new String[] { "cmd.exe", "/c", cmd };
//        // 执行命令
//        Process process = null;
//        try {
//            process = Runtime.getRuntime().exec(cmdA);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 获取返回结果
//        InputStream inputStream = process.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line = null;
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//                stringBuilder.append("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String result = stringBuilder.toString();
//        System.out.println(result);
//        return result;
//    }
}
