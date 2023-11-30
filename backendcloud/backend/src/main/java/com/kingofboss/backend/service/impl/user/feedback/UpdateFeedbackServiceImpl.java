package com.kingofboss.backend.service.impl.user.feedback;

import com.kingofboss.backend.mapper.FeedbackMapper;
import com.kingofboss.backend.pojo.Feedback;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.feedback.UpdateFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateFeedbackServiceImpl implements UpdateFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int feedback_id = Integer.parseInt(data.get("feedback_id"));

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String > map = new HashMap<>();

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


        Feedback feedback = feedbackMapper.selectById(feedback_id);

        if (feedback == null) {
            map.put("error_message", "反馈不存在或已被删除");
            return map;
        }

        if (!feedback.getUserId().equals(user.getId())) {
            map.put("error_message", "没有权限删除反馈");
            return map;
        }

        Feedback new_feedback = new Feedback(
                feedback.getId(),
                user.getId(),
                title,
                description,
                content,
                feedback.getCreatetime(),
                new Date()
        );

        feedbackMapper.updateById(new_feedback);

        map.put("error_message", "success");

        return map;
    }
}
