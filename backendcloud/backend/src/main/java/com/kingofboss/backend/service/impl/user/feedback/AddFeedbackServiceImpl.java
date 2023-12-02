package com.kingofboss.backend.service.impl.user.feedback;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.backend.mapper.FeedbackMapper;
import com.kingofboss.backend.pojo.Feedback;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.feedback.AddFeedbackService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddFeedbackServiceImpl implements AddFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    private static final String filePath = System.getProperty("user.dir") + "/feedback_attachment/";

    @Override
    public Map<String, String> add(MultipartFile file, Map<String, String> data) {
        // 查询插入者信息
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String title = data.get("title");
        String description = data.get("description");
/*        String content = data.get("content");*/

        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message","标题不能为空");
            return map;
        }

        if (title.length() > 120 ) {
            map.put("error_message","标题长度不能大于120");
            return map;
        }

        if (description == null || description.length() == 0 ) {
            description = "";
        }

        if (description.length() > 500) {
            map.put("error_message", "描述长度不能超过500");
            return map;
        }

        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        if (feedbackMapper.selectCount(queryWrapper) >= 10) {
            map.put("error_message", "每个用户最多只能创建10个Bot！");
            return map;
        }


        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();
        try {
            if (!Files.isDirectory(Paths.get(filePath))) {
                Files.createDirectories(Paths.get(filePath));
            }
            // File storage format: timestamp-file name
            Files.write(Paths.get(filePath + flag + "-" + fileName), file.getBytes());
            System.out.println(fileName + "--Upload successful");
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error_message", "File upload failed");
            return errorMap;
        }

        // 解析docx文件
        try {
            InputStream is = new FileInputStream(filePath + flag + "-" + fileName);
            XWPFDocument document = new XWPFDocument(is);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Enable external entity parsing
            dbFactory.setFeature("http://xml.org/sax/features/external-general-entities", true);
            dbFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", true);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            for (XWPFParagraph para : paragraphs) {
                String xml = para.getCTP().xmlText(); // Get XML from paragraph
                InputStream xmlIs = new ByteArrayInputStream(xml.getBytes());
                Document doc = dBuilder.parse(xmlIs);

                // Now you can use doc to navigate the XML DOM
                // ...

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 注意这里的类用java.util.Data 中的Data
        Date now = new Date();
        Feedback feedback = new Feedback((Integer) null, user.getId(), title, description, flag, now, now);

        // 保存其中的
        feedbackMapper.insert(feedback);
        map.put("error_message", "success");

        return map;
    }
}
