package com.kingofboss.backend.controller.feedback;

import com.kingofboss.backend.service.user.feedback.UpdateFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateFeedbackController {
    @Autowired
    private UpdateFeedbackService updateFeedbackService;

    @PostMapping("/api/user/feedback/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updateFeedbackService.update(data);
    }
}
