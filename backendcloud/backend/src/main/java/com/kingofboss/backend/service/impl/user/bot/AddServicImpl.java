package com.kingofboss.backend.service.impl.user.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.backend.mapper.Botmapper;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.pojo.Bot;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServicImpl implements AddService {
    @Autowired
    private Botmapper botmapper;
    @Override
    public Map<String, String> add(Map<String, String> data) {
        // 查询插入者信息
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

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
            description = "这个人很懒，什么也没有留下";
        }

        if (description.length() > 500) {
            map.put("error_message", "bot描述长度不能超过500");
            return map;
        }

        if (content == null || content.length() == 0) {
            map.put("error_message", "代码不能为空");
            return  map;
        }

        if (content.length() > 10240) {
            map.put("error_message", "代码长度不能超过10240");
            return map;
        }

        QueryWrapper<Bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        if (botmapper.selectCount(queryWrapper) >= 10) {
            map.put("error_message", "每个用户最多只能创建10个Bot！");
            return map;
        }

        // 注意这里的类用java.util.Data 中的Data
        Date now = new Date();
        Bot bot = new Bot((Integer) null, user.getId(), title, description, content, now, now);

        botmapper.insert(bot);
        map.put("error_message", "success");

        return map;
    }
}