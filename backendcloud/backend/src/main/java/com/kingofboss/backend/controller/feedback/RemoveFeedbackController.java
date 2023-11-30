package com.kingofboss.backend.controller.feedback;

import com.kingofboss.backend.service.user.feedback.RemoveFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveFeedbackController {
    @Autowired
    private RemoveFeedbackService removeFeedbackService;

    @PostMapping("/api/user/feedback/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeFeedbackService.remove(data);
    }
}
