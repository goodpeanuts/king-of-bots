package com.kingofboss.backend.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.backend.mapper.UserMapper;
import com.kingofboss.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    //如果要用到数据库中的 mapper 一定要加下面的注释 @Autowired
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/user/all/")
    public List<User> getAll() {
    return userMapper.selectList(null);
    }

    @GetMapping("/user/{userId}/")
    public List<User> getuer(@PathVariable int userId) {
        // 复杂的查询要用到queryWrapper
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("id", 2).le("id", 3);

        return userMapper.selectList(queryWrapper);
    }

}