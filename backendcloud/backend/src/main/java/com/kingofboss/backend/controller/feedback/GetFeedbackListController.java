package com.kingofboss.backend.controller.feedback;

import com.kingofboss.backend.pojo.Feedback;
import com.kingofboss.backend.service.user.feedback.GetFeedbackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetFeedbackListController {
    @Autowired
    private GetFeedbackListService getFeedbackListService;

    @GetMapping("/api/user/feedback/getlist/")
    public List<Feedback> getList(){
        return getFeedbackListService.getList();
    }
}
