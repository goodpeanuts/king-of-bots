package com.kingofboss.backend.controller.feedback;

import com.kingofboss.backend.service.user.feedback.AddFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddFeedbackController {
    @Autowired
    private AddFeedbackService addFeedbackService;

    @PostMapping("/api/user/feedback/add/")
    public Map<String, String> add(@RequestParam("file") MultipartFile file,
                                   @RequestParam("title") String title,
                                   @RequestParam("description") String description,
                                   @RequestParam("content") String content) {
        Map<String, String> data = new HashMap<>();
        data.put("title", title);
        data.put("description", description);
        data.put("content", content);
        return addFeedbackService.add(file, data);
    }

}
