package com.kingofboss.backend.service.impl.user.feedback;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.backend.mapper.FeedbackMapper;
import com.kingofboss.backend.pojo.Bot;
import com.kingofboss.backend.pojo.Feedback;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.feedback.GetFeedbackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFeedbackListServiceImpl implements GetFeedbackListService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public List<Feedback> getList() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Feedback> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());

        return feedbackMapper.selectList(queryWrapper);
    }
}
