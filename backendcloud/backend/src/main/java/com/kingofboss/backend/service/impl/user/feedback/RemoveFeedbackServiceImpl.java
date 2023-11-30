package com.kingofboss.backend.service.impl.user.feedback;

import com.kingofboss.backend.mapper.FeedbackMapper;
import com.kingofboss.backend.pojo.Bot;
import com.kingofboss.backend.pojo.Feedback;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.feedback.RemoveFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveFeedbackServiceImpl implements RemoveFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int feedback_id = Integer.parseInt(data.get("feedback_id"));
        Feedback feedback = feedbackMapper.selectById(feedback_id);
        Map<String, String> map = new HashMap<>();

        if (feedback == null) {
            map.put("error_message", "反馈不存在或已被删除");
            return map;
        }

        if (!feedback.getUserId().equals(user.getId())) {
            map.put("error_message", "没有权限删除反馈");
            return map;
        }

        feedbackMapper.deleteById(feedback_id);

        map.put("error_message", "success");
        return map;
    }
}
