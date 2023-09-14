package com.kingofboss.botrunningsystem.service.impl.user.bot;

import com.kingofboss.botrunningsystem.mapper.Botmapper;
import com.kingofboss.botrunningsystem.pojo.Bot;
import com.kingofboss.botrunningsystem.pojo.User;
import com.kingofboss.botrunningsystem.service.impl.UserDetailImpl;
import com.kingofboss.botrunningsystem.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private Botmapper botmapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));

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

        Bot bot = botmapper.selectById(bot_id);

        if (bot == null) {
            map.put("error_message", "Bot不存在或已被删除");
            return map;
        }

        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "没有权限删除Bot");
            return map;
        }

        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getCreatetime(),
                new Date()
        );

        botmapper.updateById(new_bot);

        map.put("error_message", "success");

        return map;
    }
}
