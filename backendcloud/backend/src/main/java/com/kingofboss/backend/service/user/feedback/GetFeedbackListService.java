package com.kingofboss.backend.service.user.feedback;

import com.kingofboss.backend.pojo.Bot;
import com.kingofboss.backend.pojo.Feedback;

import java.util.List;

public interface GetFeedbackListService {
    List<Feedback> getList();
}
