package com.kingofboss.backend.controller.user;


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

    @Autowired
    UserMapper userMapper;
    @RequestMapping("/user/all/")
    public List<User> getAll() {
    return userMapper.selectList(null);
    }

    @GetMapping("/user/{userId}/")
    public User getuer(@PathVariable int userId) {
        return userMapper.selectById(userId);
    }

}
